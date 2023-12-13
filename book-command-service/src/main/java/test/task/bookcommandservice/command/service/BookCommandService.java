package test.task.bookcommandservice.command.service;



import test.task.bookcommandservice.command.entity.BookCommand;

import java.util.List;


public interface BookCommandService {
    // POST
    BookCommand addBook(BookCommand book);
    List<BookCommand> addBooks(List<BookCommand> books);


    // PUT
    BookCommand updateBook(BookCommand book, long id);
    //Book updateBookByIsbn(Book book, String isbn);


    //DELETE
    void deleteBook(long id);
}