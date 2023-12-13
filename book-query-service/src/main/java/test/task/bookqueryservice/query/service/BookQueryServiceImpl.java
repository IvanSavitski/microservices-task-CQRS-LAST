package test.task.bookqueryservice.query.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import test.task.bookcommandservice.command.dto.BookCommandKafkaEvent;
import test.task.bookcommandservice.command.dto.BookCommandsListKafkaEvent;
import test.task.bookqueryservice.exception.ResourceNotFoundException;
import test.task.bookqueryservice.query.dto.BookQueriesListKafkaEvent;
import test.task.bookqueryservice.query.dto.BookQueryEvent;
import test.task.bookqueryservice.query.dto.BookQueryIdKafkaEvent;
import test.task.bookqueryservice.query.dto.BookQueryKafkaEvent;
import test.task.bookqueryservice.query.entity.BookQuery;
import test.task.bookqueryservice.query.repository.BookQueryRepository;

import java.io.DataInput;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class BookQueryServiceImpl implements BookQueryService {
    private final BookQueryRepository bookQueryRepository;

    /*@Value("${topic.get-book}")
    private String topicCreateOrder;

    @Value("${spring.kafka.consumer.group-id}}")
    private String kafkaConsumerGroupId;*/

    //private static final String topicCreateOrder = "${topic.get-book}";
    //private /*static*/ final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";


    @Autowired
    public BookQueryServiceImpl(BookQueryRepository bookQueryRepository) {
        super();
        this.bookQueryRepository = bookQueryRepository;
    }

    /*@Transactional
    @KafkaListener(topics = topicCreateOrder,
            groupId = kafkaConsumerGroupId,
            properties = {"spring.json.value.default.type=test.task.bookqueryservice.query.dto.BookQueryEvent"})
    public void processProductEvent(List<BookQueryEvent> events) {

        System.out.println("Getting events " + events);

        BookQueryKafkaEvent bookQueryKafkaEvent;
        BookQueryIdKafkaEvent bookQueryIdKafkaEvent;
        BookQueriesListKafkaEvent bookQueriesListKafkaEvent;

        try {
            for (BookQueryEvent event : events) {
                bookQueryKafkaEvent = new ObjectMapper().readValue(event, BookQueryKafkaEvent.class);
                bookQueryIdKafkaEvent = new ObjectMapper().readValue(event, BookQueryIdKafkaEvent.class);
                bookQueriesListKafkaEvent = new ObjectMapper().readValue(event, BookQueriesListKafkaEvent.class);

                System.out.println(bookQueryKafkaEvent);

                if (bookQueryKafkaEvent.getType().equals("BookAdded")) {
                    this.bookQueryRepository.save(bookQueryKafkaEvent.getBookQuery());
                }

                if (bookQueriesListKafkaEvent.getType().equals("BooksAdded")) {
                    this.bookQueryRepository.saveAll(bookQueriesListKafkaEvent.getBookQueryList());
                }

                if (bookQueryKafkaEvent.getType().equals("BookUpdated")) {
                    this.bookQueryRepository.save(bookQueryKafkaEvent.getBookQuery());
                }

                if (bookQueryIdKafkaEvent.getType().equals("BookDeleted")) {
                    this.bookQueryRepository.deleteById(bookQueryIdKafkaEvent.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*@Transactional
    @KafkaListener(topics = topicCreateOrder,
            groupId = kafkaConsumerGroupId,
            properties = {"spring.json.value.default.type=test.task.bookqueryservice.query.dto.BookQueryKafkaEvent"})
    public void processAddingBooksListQueryKafkaEvent(List<BookQueryKafkaEvent> events) {

        System.out.println("Getting events " + events);
        //BookQueryKafkaEvent bookQueryKafkaEvent;
        try {
            for (BookQueryKafkaEvent event : events) {
                //bookQueryKafkaEvent = new ObjectMapper().readValue(event, BookQueryKafkaEvent.class);
                //System.out.println(bookQueryKafkaEvent);
                System.out.println(event);

                if (event.getType().equals("BooksAdded")) {
                    BookQuery book = event.getBookQuery();
                    this.bookQueryRepository.save(book);
                    //this.bookQueryRepository.save(event.getBookQuery());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    //@Transactional
    /*
    @KafkaListener(topics = "${topic.get-book}",//topicCreateOrder,
            groupId = "book-event-group", //"${spring.kafka.consumer.group-id}")  //kafkaConsumerGroupId,
            properties = {"spring.json.value.default.type=test.task.bookcommandservice.command.dto.BookCommandsListKafkaEvent"})
    //test.task.bookqueryservice.query.dto.BookQueriesListKafkaEvent"})
    */









    ///WORKING METHODS
    /*@KafkaListener(topics = "book_event_topic", groupId = "book_event_group",
            properties = {"spring.json.value.default.type=test.task.bookcommandservice.command.dto.BookCommandsListKafkaEvent"})
    public void processAddingBooksListQueryKafkaEvent(BookCommandsListKafkaEvent bookCommandEvent) {  //(BookQueriesListKafkaEvent event) {

        System.out.println("Getting event " + bookCommandEvent);
        BookQueriesListKafkaEvent bookQueryEvent;

        try {
            if (bookCommandEvent.getType().equals("BooksAdded") && bookCommandEvent.getBookCommandList() != null) {
                ModelMapper modelMapper = new ModelMapper();
                bookQueryEvent = modelMapper.map(bookCommandEvent, BookQueriesListKafkaEvent.class);
                //bookQueryEvent = new ObjectMapper().readValue(bookCommandEvent, BookQueriesListKafkaEvent.class);
                System.out.println("To repo " + bookQueryEvent);
                this.bookQueryRepository.saveAll(bookQueryEvent.getBookQueryList());
                System.out.println("Books List was successfully received from producer by BookCommandsListKafkaEvent and also successfully saved to consumer repository");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "book_event_topic", groupId = "book_event_group",
            properties = {"spring.json.value.default.type=test.task.bookcommandservice.command.dto.BookCommandKafkaEvent"})
    public void processAddingBookQueryKafkaEvent(BookCommandKafkaEvent bookCommandEvent) {

        System.out.println("Getting event " + bookCommandEvent);
        BookQueryKafkaEvent bookQueryEvent;

        try {
            if (bookCommandEvent.getType().equals("BookAdded") && bookCommandEvent.getBookCommand() != null) {
                ModelMapper modelMapper = new ModelMapper();
                bookQueryEvent = modelMapper.map(bookCommandEvent, BookQueryKafkaEvent.class);
                //bookQueryEvent = new ObjectMapper().readValue(bookCommandEvent, BookQueriesListKafkaEvent.class);
                System.out.println("To repo " + bookQueryEvent);
                this.bookQueryRepository.save(bookQueryEvent.getBookQuery());
                System.out.println("Book was successfully received from producer by BookCommandKafkaEvent and also successfully saved to consumer repository");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "book_event_topic", groupId = "book_event_group",
            properties = {"spring.json.value.default.type=test.task.bookcommandservice.command.dto.BookCommandKafkaEvent"})
    public void processUpdatingBookQueryKafkaEvent(BookCommandKafkaEvent bookCommandEvent) {

        System.out.println("Getting event " + bookCommandEvent);
        BookQueryKafkaEvent bookQueryEvent;

        try {
            if (bookCommandEvent.getType().equals("BookUpdated") && bookCommandEvent.getBookCommand() != null) {
                ModelMapper modelMapper = new ModelMapper();
                bookQueryEvent = modelMapper.map(bookCommandEvent, BookQueryKafkaEvent.class);

                System.out.println("Updating repo " + bookQueryEvent);
                this.bookQueryRepository.save(bookQueryEvent.getBookQuery());
                System.out.println("Book was successfully received from producer by BookCommandKafkaEvent and also successfully saved to consumer repository");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/







    ////UNWORKING METHODS

    /*    @KafkaListener(topics = "${topic.get-book}",//topicCreateOrder,
            groupId = "book-event-group", //"${spring.kafka.consumer.group-id}")  //kafkaConsumerGroupId,
            properties = {"spring.json.value.default.type=test.task.bookcommandservice.command.dto.BookCommandsListKafkaEvent"})
                     //test.task.bookqueryservice.query.dto.BookQueriesListKafkaEvent"})
    public void processAddingBooksListQueryKafkaEvent(BookCommandsListKafkaEvent bookEvent) {  //(BookQueriesListKafkaEvent event) {

        System.out.println("Getting event" + bookEvent);
        BookQueriesListKafkaEvent bookQueriesListKafkaEvent = null;


        //BookQueryKafkaEvent bookQueryKafkaEvent;
        try {
            System.out.println(bookEvent);
            while(bookEvent.getBookCommandList() == null || event.getBookQueryList().isEmpty()) {
                if (event.getType().equals("BooksAdded") && event.getBookQueryList() != null) {
                    //if (event.getType().equals("BooksAdded")) {
                    //BookQuery book = event.getBookQueryList();
                    //this.bookQueryRepository.save(book);

                    //this.bookQueryRepository.save(event.getBookQuery());
                    this.bookQueryRepository.saveAll(event.getBookQueryList());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/







    // GET
    @Override
    public List<BookQuery> getAllBooks() {
        return bookQueryRepository.findAll();
    }

    @Override
    public Optional<BookQuery> getBookById(long id) {
        Optional<BookQuery> optionalBook = bookQueryRepository.findById(id);
        return Optional.ofNullable(optionalBook.orElseThrow(() -> new ResourceNotFoundException("Book", "Isbn", id)));
    }

    @Override
    public Optional<BookQuery> getBookByIsbn(String isbn) {
        Optional<BookQuery> optionalBook = bookQueryRepository.findByIsbn(isbn);
        return Optional.ofNullable(optionalBook.orElseThrow(() -> new ResourceNotFoundException("Book", "Isbn", isbn)));
    }
}