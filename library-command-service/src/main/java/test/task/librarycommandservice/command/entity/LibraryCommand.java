package test.task.librarycommandservice.command.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.task.bookcommandservice.command.entity.BookCommand;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "library_command")
@AllArgsConstructor
@NoArgsConstructor
public class LibraryCommand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private BookCommand book;

    @Column(name = "borrowed_time")
    private LocalDateTime borrowedTime;

    @Column(name = "return_time")
    private LocalDateTime returnTime;

    @Column(name = "days_period")
    private Long daysPeriod;
}