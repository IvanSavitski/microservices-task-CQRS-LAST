package test.task.libraryqueryservice.query.listeners;


import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import test.task.librarycommandservice.command.dto.LibraryCommandKafkaEvent;
import test.task.libraryqueryservice.query.dto.LibraryQueryKafkaEvent;
import test.task.libraryqueryservice.query.repository.LibraryQueryRepository;

@Component
@Slf4j
public class AddingBookToLibraryKafkaEventListener {
    private LibraryQueryRepository libraryQueryRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public AddingBookToLibraryKafkaEventListener(LibraryQueryRepository libraryQueryRepository) {
        super();
        this.libraryQueryRepository = libraryQueryRepository;
    }

    @Transactional
    @KafkaListener(topics = "book_added_to_library_topic", groupId = "library_event_group",
            properties = {"spring.json.value.default.type=test.task.librarycommandservice.command.dto.LibraryCommandKafkaEvent"})
    public void processAddingBookToLibraryQueryKafkaEvent(LibraryCommandKafkaEvent libraryCommandEvent) {
        System.out.println("Getting event " + libraryCommandEvent);

        try {
            if (libraryCommandEvent.getType().equals("BookAddedToLibrary") && libraryCommandEvent.getLibraryCommand() != null) {
                LibraryQueryKafkaEvent libraryQueryEvent = modelMapper.map(libraryCommandEvent, LibraryQueryKafkaEvent.class);
                System.out.println("To repo " + libraryQueryEvent);
                this.libraryQueryRepository.save(libraryQueryEvent.getLibraryQuery());
                log.info("Book was successfully received from producer by LibraryCommandKafkaEvent, book adding to library and also successfully saved to consumer repository");
                log.info("Book added to library with id: {}", libraryQueryEvent.getLibraryQuery().getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



