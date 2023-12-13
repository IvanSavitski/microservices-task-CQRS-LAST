package test.task.bookcommandservice.command.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import test.task.bookcommandservice.command.dto.BookCommandEvent;
import test.task.bookcommandservice.command.dto.BookCommandIdKafkaEvent;
import test.task.bookcommandservice.command.dto.BookCommandKafkaEvent;
import test.task.bookcommandservice.command.dto.BookCommandsListKafkaEvent;
import test.task.bookcommandservice.command.entity.BookCommand;
import test.task.bookcommandservice.command.repository.BookCommandRepository;
import test.task.bookcommandservice.exception.ResourceNotFoundException;

import java.util.List;


@Service
@Transactional
public class BookCommandServiceImpl implements BookCommandService {
    private final BookCommandRepository bookCommandRepository;
    private final KafkaTemplate<String, BookCommandKafkaEvent> kafkaTemplate;
    private final KafkaTemplate<String, BookCommandsListKafkaEvent> kafkaListTemplate;
    private final KafkaTemplate<Object, BookCommandIdKafkaEvent> kafkaIdTemplate;


    @Autowired
    public BookCommandServiceImpl(BookCommandRepository bookCommandRepository,
                                  KafkaTemplate<String, BookCommandKafkaEvent> kafkaTemplate,
                                  KafkaTemplate<String, BookCommandsListKafkaEvent> kafkaListTemplate,
                                  KafkaTemplate<Object, BookCommandIdKafkaEvent> kafkaIdTemplate) {
        super();
        this.bookCommandRepository = bookCommandRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaListTemplate = kafkaListTemplate;
        this.kafkaIdTemplate = kafkaIdTemplate;
    }


    // POST
    @Override
    public BookCommand addBook(BookCommand book) {
        bookCommandRepository.save(book);
        BookCommandKafkaEvent event = new BookCommandKafkaEvent("BookAdded", book);
        this.kafkaTemplate.send("book_added_topic", event);
        return book;
        //return bookCommandRepository.save(book);
    }

    @Override
    public List<BookCommand> addBooks(List<BookCommand> books) {
        List<BookCommand> booksList = bookCommandRepository.saveAll(books);
        BookCommandsListKafkaEvent event = new BookCommandsListKafkaEvent("BooksAdded", books);
        this.kafkaListTemplate.send("books_added_topic", event);
        return booksList;
        //return bookCommandRepository.saveAll(books);
    }


    // PUT
    @Override
    public BookCommand updateBook(BookCommand book, long id) {
        BookCommand existingBook = bookCommandRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book", "Id", id));

        // обновление информации о книге
        existingBook.setIsbn(book.getIsbn());
        existingBook.setTitle(book.getTitle());
        existingBook.setGenre(book.getGenre());
        existingBook.setDescription(book.getDescription());
        existingBook.setAuthor(book.getAuthor());

        existingBook = bookCommandRepository.save(existingBook);

        BookCommandKafkaEvent event = new BookCommandKafkaEvent("BookUpdated", existingBook);
        this.kafkaTemplate.send("book_updated_topic", event);

        return existingBook;
    }


    // DELETE
    @Override
    public void deleteBook(long id) {
        bookCommandRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book", "Id", id));
        bookCommandRepository.deleteById(id);

        BookCommandIdKafkaEvent eventId = new BookCommandIdKafkaEvent("BookDeleted", id);
        this.kafkaIdTemplate.send("book_deleted_topic", eventId);
        //return deletedBook;
    }
}