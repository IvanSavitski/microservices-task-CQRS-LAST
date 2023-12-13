package test.task.libraryqueryservice.query.listeners;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import test.task.librarycommandservice.command.dto.LibraryCommandIdKafkaEvent;
import test.task.libraryqueryservice.query.dto.LibraryQueryIdKafkaEvent;
import test.task.libraryqueryservice.query.repository.LibraryQueryRepository;

@Component
@Slf4j
public class DeletingBookFromLibraryKafkaEventListener {
    private LibraryQueryRepository libraryQueryRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public DeletingBookFromLibraryKafkaEventListener(LibraryQueryRepository libraryQueryRepository) {
        super();
        this.libraryQueryRepository = libraryQueryRepository;
    }
    @Transactional
    @KafkaListener(topics = "book_deleted_from_library_topic",
            groupId = "library_event_group",
            properties = {"spring.json.value.default.type=test.task.librarycommandservice.command.dto.LibraryCommandIdKafkaEvent"})
    public void processProductEvent(LibraryCommandIdKafkaEvent libraryCommandEvent) {
        System.out.println("Getting event " + libraryCommandEvent);

        try {
            if (libraryCommandEvent.getType().equals("BookDeletedFromLibrary")) {

                LibraryQueryIdKafkaEvent libraryQueryEvent = modelMapper.map(libraryCommandEvent, LibraryQueryIdKafkaEvent.class);
                System.out.println("To repo " + libraryQueryEvent);
                this.libraryQueryRepository.deleteById(libraryQueryEvent.getId());
                log.info("Book was successfully received from producer by LibraryCommandIdKafkaEvent, book deleting from library consumer repository");
                log.info("Book deleted from library: {}", libraryQueryEvent.getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
