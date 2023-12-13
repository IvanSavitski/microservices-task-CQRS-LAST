package test.task.libraryqueryservice.query.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.task.bookqueryservice.query.entity.BookQuery;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "library_query")
@AllArgsConstructor
@NoArgsConstructor
public class LibraryQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private BookQuery book;

    @Column(name = "borrowed_time")
    private LocalDateTime borrowedTime;

    @Column(name = "return_time")
    private LocalDateTime returnTime;

    @Column(name = "days_period")
    private Long daysPeriod;
}
