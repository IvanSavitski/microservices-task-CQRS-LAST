package test.task.librarycommandservice.command.dto;

import lombok.Data;
import test.task.bookcommandservice.command.entity.BookCommand;

import java.time.LocalDateTime;


@Data
public class LibraryCommandEvent {
    private Long id;
    private BookCommand book;
    private LocalDateTime borrowedTime;
    private LocalDateTime returnTime;
    private Long daysPeriod;
}
