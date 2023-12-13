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

    @Autowired
    public BookQueryServiceImpl(BookQueryRepository bookQueryRepository) {
        super();
        this.bookQueryRepository = bookQueryRepository;
    }

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