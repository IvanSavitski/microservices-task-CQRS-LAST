package test.task.librarycommandservice.command.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import test.task.bookcommandservice.command.entity.BookCommand;
import test.task.bookcommandservice.command.repository.BookCommandRepository;
import test.task.bookcommandservice.exception.ResourceNotFoundException;
import test.task.librarycommandservice.command.dto.LibraryAndBookCommandKafkaEvent;
import test.task.librarycommandservice.command.dto.LibraryCommandIdKafkaEvent;
import test.task.librarycommandservice.command.dto.LibraryCommandKafkaEvent;
import test.task.librarycommandservice.command.dto.LibraryCommandsListKafkaEvent;
import test.task.librarycommandservice.command.entity.LibraryCommand;
import test.task.librarycommandservice.command.repository.LibraryCommandRepository;

import test.task.librarycommandservice.exception.BookLibraryException;


import java.time.LocalDateTime;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;



@Service
@Transactional
@Slf4j
public class LibraryCommandServiceImpl implements LibraryCommandService {

    private final BookCommandRepository bookCommandRepository;
    private static final Long DAYS = 14L;
    private final LibraryCommandRepository libraryCommandRepository;
    private final WebClient webClient;


    private final KafkaTemplate<String, LibraryCommandKafkaEvent> kafkaTemplate;
    private final KafkaTemplate<String, LibraryCommandsListKafkaEvent> kafkaListTemplate;
    private final KafkaTemplate<Object, LibraryCommandIdKafkaEvent> kafkaIdTemplate;
    private final KafkaTemplate<String, LibraryAndBookCommandKafkaEvent> layeredKafkaTemplate;


    @Autowired
    public LibraryCommandServiceImpl(LibraryCommandRepository libraryCommandRepository,
                                     WebClient.Builder webClientBuilder,
                                     BookCommandRepository bookCommandRepository,
                                     KafkaTemplate<String, LibraryCommandKafkaEvent> kafkaTemplate,
                                     KafkaTemplate<String, LibraryCommandsListKafkaEvent> kafkaListTemplate,
                                     KafkaTemplate<Object, LibraryCommandIdKafkaEvent> kafkaIdTemplate,
                                     KafkaTemplate<String, LibraryAndBookCommandKafkaEvent> layeredKafkaTemplate
    ) {
        this.libraryCommandRepository = libraryCommandRepository;
        this.webClient = webClientBuilder.build();

        this.bookCommandRepository = bookCommandRepository;

        this.kafkaTemplate = kafkaTemplate;
        this.kafkaListTemplate = kafkaListTemplate;
        this.kafkaIdTemplate = kafkaIdTemplate;
        this.layeredKafkaTemplate = layeredKafkaTemplate;
    }


    public void addBookToLibrary(long bookId) {
        Optional<LibraryCommand> libraryBookMatch = libraryCommandRepository.findById(bookId);

        if (libraryBookMatch.isPresent()) {
            log.info("Book is already added to library");
            throw new BookLibraryException("Book is already added to library", bookId);
        }


        // Отправить запрос на получение информации о книге по ее ID
        Mono<BookCommand> bookMono = webClient.get()
                .uri("http://localhost:8082/api/books/query/get/getById/{bookId}", bookId)
                .retrieve()
                .bodyToMono(BookCommand.class);

        bookMono.subscribe(book -> {
            // Создать новую запись в библиотеке для данной книги
            LibraryCommand libraryBook = new LibraryCommand();
            libraryBook.setBook(book);
            libraryBook.setBorrowedTime(null);
            libraryBook.setDaysPeriod(DAYS);
            libraryBook.setReturnTime(null);

            libraryBook = libraryCommandRepository.save(libraryBook);
            LibraryCommandKafkaEvent event = new LibraryCommandKafkaEvent("BookAddedToLibrary", libraryBook);
            this.kafkaTemplate.send("book_added_to_library_topic", event);
            //return libraryBook;
        }, error -> {
            throw new ResourceNotFoundException("Library", "bookId", bookId);
        });
    }



    public void borrowBook(long bookId) {
        Optional<LibraryCommand> libraryBookMatch = libraryCommandRepository.findById(bookId);
        Optional<BookCommand> bookMatch = bookCommandRepository.findById(bookId);

        if (libraryBookMatch.isPresent()) {
            log.info("Book is exist in library");
        }

        if(bookMatch.get().isBorrowed()) {
            log.info("Book is already borrowed");
            throw new BookLibraryException("Book is already borrowed", bookId);
        }

        // Отправить запрос на получение информации о книге по ее ID
        Mono<BookCommand> bookMono = webClient.get()
                .uri("http://localhost:8082/api/books/query/get/getById/{bookId}", bookId)
                .retrieve()
                .bodyToMono(BookCommand.class);

        bookMono.subscribe(book -> {
            // Обновить информацию о книге в библиотеке
            LibraryCommand libraryBook = libraryBookMatch.get();
            libraryBook.setBorrowedTime(LocalDateTime.now());
            libraryBook.setDaysPeriod(DAYS);
            //libraryBook.setReturnTime(LocalDateTime.now().plusDays(libraryBook.getDaysPeriod()));
            libraryBook.setReturnTime(LocalDateTime.now().plusSeconds(libraryBook.getDaysPeriod()));
            BookCommand bookExmp = bookMatch.get();
            bookExmp.setBorrowed(true);

            libraryCommandRepository.save(libraryBook);
            bookCommandRepository.save(bookExmp);

            LibraryAndBookCommandKafkaEvent event = new LibraryAndBookCommandKafkaEvent("BookBorrowedFromLibrary", libraryBook, bookExmp);
            this.layeredKafkaTemplate.send("book_borrowed_from_library_topic", event);
            //LibraryCommandKafkaEvent event = new LibraryCommandKafkaEvent("BookBorrowedToLibrary", libraryBook);
            //this.kafkaTemplate.send(topic, event);

            // Автоматический возврат книги через заданный период времени
            CompletableFuture.delayedExecutor(libraryBook.getDaysPeriod(), TimeUnit.SECONDS)
                    .execute(() -> returnBook(bookExmp.getId(), libraryBook.getId()));
        }, error -> {
            throw new ResourceNotFoundException("Library", "bookId", bookId);
        });
    }


    public void returnBook(long bookId, long libraryBookId) {
        BookCommand book = bookCommandRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book", "bookId", bookId));;
        LibraryCommand libraryBook = libraryCommandRepository.findById(libraryBookId).orElseThrow(
                () -> new ResourceNotFoundException("Library Book", "libraryBookId", libraryBookId));

        book.setBorrowed(false);

        libraryBook.setBook(book);
        libraryBook.setBorrowedTime(null);
        libraryBook.setDaysPeriod(DAYS);
        libraryBook.setReturnTime(null);

        libraryCommandRepository.save(libraryBook);
        bookCommandRepository.save(book);

        LibraryAndBookCommandKafkaEvent event = new LibraryAndBookCommandKafkaEvent("BookReturned", libraryBook, book);
        this.layeredKafkaTemplate.send("book_returned_to_library_topic", event);

        //LibraryCommandKafkaEvent event = new LibraryCommandKafkaEvent("BookReturned", libraryBook);
        //this.kafkaTemplate.send(topic, event);
    }

    public void deleteBook(long bookId) {
        libraryCommandRepository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException("Library", "bookId", bookId));
        libraryCommandRepository.deleteById(bookId);

        LibraryCommandIdKafkaEvent event = new LibraryCommandIdKafkaEvent("BookDeletedFromLibrary", bookId);
        this.kafkaIdTemplate.send("book_deleted_from_library_topic", event);
    }
}
