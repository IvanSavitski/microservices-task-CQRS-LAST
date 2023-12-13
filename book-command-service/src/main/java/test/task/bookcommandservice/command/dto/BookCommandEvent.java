package test.task.bookcommandservice.command.dto;

import lombok.Data;

@Data
public class BookCommandEvent {
    private Long id;
    private String isbn;
    private String title;
    private String genre;
    private String description;
    private String author;
    private boolean isBorrowed;
}

