package test.task.bookqueryservice.query.service;


import test.task.bookcommandservice.command.dto.BookCommandsListKafkaEvent;
import test.task.bookqueryservice.query.dto.BookQueriesListKafkaEvent;
import test.task.bookqueryservice.query.dto.BookQueryEvent;
import test.task.bookqueryservice.query.dto.BookQueryKafkaEvent;
import test.task.bookqueryservice.query.entity.BookQuery;

import java.util.List;
import java.util.Optional;

public interface BookQueryService {
    // GET
    List<BookQuery> getAllBooks();
    Optional<BookQuery> getBookById(long id);
    Optional<BookQuery> getBookByIsbn(String isbn);
}
