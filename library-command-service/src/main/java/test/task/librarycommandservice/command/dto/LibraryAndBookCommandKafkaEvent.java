package test.task.librarycommandservice.command.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import test.task.bookcommandservice.command.entity.BookCommand;
import test.task.librarycommandservice.command.entity.LibraryCommand;

@Data
@NoArgsConstructor
public class LibraryAndBookCommandKafkaEvent {
    public LibraryAndBookCommandKafkaEvent(String type, LibraryCommand library, BookCommand book) {
        this.type = type;
        this.library = library;
        this.book = book;
    }

    private String type;
    private LibraryCommand library;
    private BookCommand book;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public LibraryCommand getLibraryCommand() {
        return library;
    }
    public void setLibraryCommand(LibraryCommand library) {
        this.library = library;
    }

    public BookCommand getBookCommand() {
        return book;
    }
    public void setBookCommand(BookCommand book) {
        this.book = book;
    }
}

