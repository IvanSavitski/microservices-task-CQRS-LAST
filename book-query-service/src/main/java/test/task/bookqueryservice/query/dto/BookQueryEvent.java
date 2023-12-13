package test.task.bookqueryservice.query.dto;

import lombok.Data;

@Data
public class BookQueryEvent {
    private Long id;
    private String isbn;
    private String title;
    private String genre;
    private String description;
    private String author;
    private boolean isBorrowed;
}
