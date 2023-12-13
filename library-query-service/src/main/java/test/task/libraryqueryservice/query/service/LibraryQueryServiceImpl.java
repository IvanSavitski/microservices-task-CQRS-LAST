package test.task.libraryqueryservice.query.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;

import test.task.libraryqueryservice.query.dto.LibraryAndBookQueryKafkaEvent;
import test.task.libraryqueryservice.query.dto.LibraryQueryIdKafkaEvent;
import test.task.libraryqueryservice.query.dto.LibraryQueryKafkaEvent;
import test.task.libraryqueryservice.query.entity.LibraryQuery;
import test.task.libraryqueryservice.query.repository.LibraryQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import test.task.bookqueryservice.query.entity.BookQuery;
import test.task.bookqueryservice.query.repository.BookQueryRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LibraryQueryServiceImpl implements LibraryQueryService {
    private static final Long DAYS = 60L; //14L;
    private final LibraryQueryRepository libraryQueryRepository;
    private final BookQueryRepository bookQueryRepository;
    //private final WebClient webClient;


   // private static final String topicCreateOrder = "${topic.send-order}";
    //private static final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";


    @Autowired
    public LibraryQueryServiceImpl(LibraryQueryRepository libraryQueryRepository, BookQueryRepository bookQueryRepository) {
        this.libraryQueryRepository = libraryQueryRepository;
        //this.webClient = webClientBuilder.build();
        this.bookQueryRepository = bookQueryRepository;
    }

    /*@Transactional
    @KafkaListener(topics = topicCreateOrder,
            groupId = kafkaConsumerGroupId,
            properties = {"spring.json.value.default.type=test.task.libraryqueryservice.query.dto.LibraryQueryEvent"})
    public void processProductEvent(String event) {

        System.out.println("Getting event " + event);

        LibraryQueryKafkaEvent libraryQueryKafkaEvent;
        LibraryQueryIdKafkaEvent libraryQueryIdKafkaEvent;
        LibraryAndBookQueryKafkaEvent libraryAndBookQueryKafkaEvent;

        try {
            libraryQueryKafkaEvent = new ObjectMapper().readValue(event, LibraryQueryKafkaEvent.class);
            libraryQueryIdKafkaEvent =  new ObjectMapper().readValue(event, LibraryQueryIdKafkaEvent.class);
            libraryAndBookQueryKafkaEvent =  new ObjectMapper().readValue(event, LibraryAndBookQueryKafkaEvent.class);

            System.out.println(libraryQueryKafkaEvent);

            if (libraryQueryKafkaEvent.getType().equals("BookAddedToLibrary")) {
                this.libraryQueryRepository.save(libraryQueryKafkaEvent.getLibraryQuery());
                // Обновить репозиторий книг
                //BookQuery book = libraryBook.getBook();
                //book.setBorrowed(true);
                //bookQueryRepository.save(book);
                log.info("Book added to library: {}", libraryQueryKafkaEvent.getLibraryQuery().getId());
            }

            if (libraryQueryKafkaEvent.getType().equals("BookBorrowedFromLibrary")) {
                this.libraryQueryRepository.save(libraryAndBookQueryKafkaEvent.getLibraryQuery());
                this.bookQueryRepository.save(libraryAndBookQueryKafkaEvent.getBookQuery());

                log.info("Book borrowed from library: {}  {}", libraryAndBookQueryKafkaEvent.getBookQuery(), libraryAndBookQueryKafkaEvent.getBookQuery());
            }

            if (libraryQueryKafkaEvent.getType().equals("BookReturned")) {
                this.libraryQueryRepository.save(libraryAndBookQueryKafkaEvent.getLibraryQuery());
                this.bookQueryRepository.save(libraryAndBookQueryKafkaEvent.getBookQuery());

                log.info("Book returned to library: {}  {}", libraryAndBookQueryKafkaEvent.getBookQuery(), libraryAndBookQueryKafkaEvent.getBookQuery());
            }

            if (libraryQueryIdKafkaEvent.getType().equals("BookDeletedFromLibrary")) {
                this.libraryQueryRepository.deleteById(libraryQueryIdKafkaEvent.getId());

                log.info("Book deleted from library: {}", libraryQueryIdKafkaEvent.getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    public List<BookQuery> getAvailableBooks() {
        List<LibraryQuery> libraryBooks = libraryQueryRepository.findAll();
        List<BookQuery> availableBooks = libraryBooks.stream()
                // .filter(libraryBook -> libraryBook.getReturnTime().isBefore(LocalDateTime.now()))
                .filter(libraryBook -> libraryBook.getBorrowedTime() == null && libraryBook.getReturnTime() == null)
                .map(LibraryQuery::getBook)
                .collect(Collectors.toList());

        return availableBooks;
    }


    public List<BookQuery> getAllBorrowedBooks() {
        List<LibraryQuery> libraryBooks = libraryQueryRepository.findAll();
        List<BookQuery> borrowedBooks = libraryBooks.stream()
                //.filter(libraryBook -> libraryBook.getReturnTime().isAfter(LocalDateTime.now()))
                .filter(libraryBook -> libraryBook.getBorrowedTime() != null && libraryBook.getReturnTime() != null)
                .map(LibraryQuery::getBook)
                .collect(Collectors.toList());

        return borrowedBooks;
    }
}
