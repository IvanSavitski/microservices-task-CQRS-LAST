package test.task.libraryqueryservice.query.listeners;


import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import test.task.bookqueryservice.query.repository.BookQueryRepository;
import test.task.librarycommandservice.command.dto.LibraryAndBookCommandKafkaEvent;
import test.task.libraryqueryservice.query.dto.LibraryAndBookQueryKafkaEvent;
import test.task.libraryqueryservice.query.repository.LibraryQueryRepository;

@Component
@Slf4j
public class BorrowingBookFromLibraryKafkaEventListener {
    private LibraryQueryRepository libraryQueryRepository;
    private BookQueryRepository bookQueryRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public BorrowingBookFromLibraryKafkaEventListener(LibraryQueryRepository libraryQueryRepository, BookQueryRepository bookQueryRepository) {
        super();
        this.libraryQueryRepository = libraryQueryRepository;
        this.bookQueryRepository = bookQueryRepository;
    }

    @Transactional
    @KafkaListener(topics = "book_borrowed_from_library_topic",
            groupId = "library_event_group",
            properties = {"spring.json.value.default.type=test.task.librarycommandservice.command.dto.LibraryAndBookCommandKafkaEvent"})
    public void processProductEvent(LibraryAndBookCommandKafkaEvent libraryCommandEvent) {
        System.out.println("Getting event " + libraryCommandEvent);

        try {
            if (libraryCommandEvent.getType().equals("BookBorrowedFromLibrary") && libraryCommandEvent.getLibraryCommand() != null) {
                LibraryAndBookQueryKafkaEvent libraryQueryEvent = modelMapper.map(libraryCommandEvent, LibraryAndBookQueryKafkaEvent.class);
                System.out.println("To repo " + libraryQueryEvent);

                this.libraryQueryRepository.save(libraryQueryEvent.getLibraryQuery());
                this.bookQueryRepository.save(libraryQueryEvent.getBookQuery());

                log.info("Book borrowed from library: {}  {}", libraryQueryEvent.getBookQuery(), libraryQueryEvent.getLibraryQuery());
                log.info("Book was successfully received from producer by LibraryAndBookCommandKafkaEvent and also successfully saved to consumer repository");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

