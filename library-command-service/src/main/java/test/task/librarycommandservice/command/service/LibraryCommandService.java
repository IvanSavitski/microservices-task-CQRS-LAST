package test.task.librarycommandservice.command.service;

public interface LibraryCommandService {
    void addBookToLibrary(long bookId);
    void borrowBook(long bookId);
    void returnBook(long bookId, long libraryBookId);
    void deleteBook(long bookId);
}

