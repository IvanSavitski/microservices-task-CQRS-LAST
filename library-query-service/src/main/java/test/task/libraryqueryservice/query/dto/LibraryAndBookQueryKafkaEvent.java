package test.task.libraryqueryservice.query.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import test.task.bookqueryservice.query.entity.BookQuery;
import test.task.libraryqueryservice.query.entity.LibraryQuery;

@Data
@NoArgsConstructor
public class LibraryAndBookQueryKafkaEvent {
    public LibraryAndBookQueryKafkaEvent(String type, LibraryQuery library, BookQuery book) {
        this.type = type;
        this.library = library;
        this.book = book;
    }

    private String type;
    private LibraryQuery library;
    private BookQuery book;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public LibraryQuery getLibraryQuery() {
        return library;
    }
    public void setLibraryQuery(LibraryQuery library) {
        this.library = library;
    }

    public BookQuery getBookQuery() {
        return book;
    }
    public void setBookQuery(BookQuery book) {
        this.book = book;
    }
}


