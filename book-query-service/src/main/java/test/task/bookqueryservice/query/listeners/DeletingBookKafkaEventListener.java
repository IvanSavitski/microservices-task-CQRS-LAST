package test.task.bookqueryservice.query.listeners;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import test.task.bookcommandservice.command.dto.BookCommandIdKafkaEvent;
import test.task.bookqueryservice.query.dto.BookQueryIdKafkaEvent;
import test.task.bookqueryservice.query.repository.BookQueryRepository;

@Component
public class DeletingBookKafkaEventListener {
    private BookQueryRepository bookQueryRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public DeletingBookKafkaEventListener(BookQueryRepository bookQueryRepository) {
        super();
        this.bookQueryRepository = bookQueryRepository;
    }

    @Transactional
    @KafkaListener(topics = "book_deleted_topic", groupId = "book_event_group",
            properties = {"spring.json.value.default.type=test.task.bookcommandservice.command.dto.BookCommandIdKafkaEvent"})
    public void processDeletingBookQueryKafkaEvent(BookCommandIdKafkaEvent bookCommandEvent) {
        System.out.println("Getting events " + bookCommandEvent);

        try {
            if (bookCommandEvent.getType().equals("BookDeleted")) {
                BookQueryIdKafkaEvent bookQueryIdKafkaEvent = modelMapper.map(bookCommandEvent, BookQueryIdKafkaEvent.class);
                System.out.println("To repo " + bookQueryIdKafkaEvent);
                long id = bookQueryIdKafkaEvent.getId();
                System.out.println(id);
                this.bookQueryRepository.deleteById(id);//bookQueryIdKafkaEvent.getId());
                System.out.println("Book was successfully received from producer by BookCommandKafkaEvent and also successfully deleted from consumer repository");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
