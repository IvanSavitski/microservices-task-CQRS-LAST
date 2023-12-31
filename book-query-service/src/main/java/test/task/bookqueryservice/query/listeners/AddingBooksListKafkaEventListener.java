package test.task.bookqueryservice.query.listeners;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import test.task.bookcommandservice.command.dto.BookCommandsListKafkaEvent;
import test.task.bookqueryservice.query.dto.BookQueriesListKafkaEvent;
import test.task.bookqueryservice.query.repository.BookQueryRepository;

@Component
public class AddingBooksListKafkaEventListener {
    private BookQueryRepository bookQueryRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public AddingBooksListKafkaEventListener(BookQueryRepository bookQueryRepository) {
        super();
        this.bookQueryRepository = bookQueryRepository;
    }

    @Transactional
    @KafkaListener(topics = "books_added_topic", groupId = "book_event_group",
            properties = {"spring.json.value.default.type=test.task.bookcommandservice.command.dto.BookCommandsListKafkaEvent"})
    public void processAddingBooksListQueryKafkaEvent(BookCommandsListKafkaEvent bookCommandEvent) {
        System.out.println("Getting event " + bookCommandEvent);

        try {
            if (bookCommandEvent.getType().equals("BooksAdded") && bookCommandEvent.getBookCommandList() != null) {
                BookQueriesListKafkaEvent bookQueryEvent = modelMapper.map(bookCommandEvent, BookQueriesListKafkaEvent.class);
                System.out.println("To repo " + bookQueryEvent);
                this.bookQueryRepository.saveAll(bookQueryEvent.getBookQueryList());
                System.out.println("Books List was successfully received from producer by BookCommandsListKafkaEvent and also successfully saved to consumer repository");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
