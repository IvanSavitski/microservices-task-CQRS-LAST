package test.task.bookqueryservice.query.listeners;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import test.task.bookcommandservice.command.dto.BookCommandKafkaEvent;
import test.task.bookqueryservice.query.dto.BookQueryKafkaEvent;
import test.task.bookqueryservice.query.repository.BookQueryRepository;


@Component
        //@KafkaListener(topics = "book_event_topic", groupId = "book_event_group",
        //properties = {"spring.json.value.default.type=test.task.bookcommandservice.command.dto.BookCommandKafkaEvent"})
public class AddingBookKafkaEventListener {
    private BookQueryRepository bookQueryRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public AddingBookKafkaEventListener(BookQueryRepository bookQueryRepository) {
        super();
        this.bookQueryRepository = bookQueryRepository;
    }

    @Transactional
    @KafkaListener(topics = "book_added_topic", groupId = "book_event_group",
            properties = {"spring.json.value.default.type=test.task.bookcommandservice.command.dto.BookCommandKafkaEvent"})
    public void processAddingBookQueryKafkaEvent(BookCommandKafkaEvent bookCommandEvent) {
        System.out.println("Getting event " + bookCommandEvent);

        try {
            if (bookCommandEvent.getType().equals("BookAdded") && bookCommandEvent.getBookCommand() != null) {
                BookQueryKafkaEvent bookQueryEvent = modelMapper.map(bookCommandEvent, BookQueryKafkaEvent.class);
                System.out.println("To repo " + bookQueryEvent);
                this.bookQueryRepository.save(bookQueryEvent.getBookQuery());
                System.out.println("Book was successfully received from producer by BookCommandKafkaEvent and also successfully saved to consumer repository");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


