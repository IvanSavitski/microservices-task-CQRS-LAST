package test.task.libraryqueryservice.query.service;

import test.task.bookqueryservice.query.entity.BookQuery;

import java.util.List;

public interface LibraryQueryService {
    List<BookQuery> getAvailableBooks();
    List<BookQuery> getAllBorrowedBooks();
}

