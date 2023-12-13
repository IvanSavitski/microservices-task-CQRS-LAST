package test.task.bookqueryservice.query.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "book_query")
@AllArgsConstructor
@NoArgsConstructor
public class BookQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "isbn")
    private String isbn;

    @Column(nullable = false, length = 200, name = "title")
    private String title;

    @Column(nullable = false, length = 200, name = "genre")
    private String genre;

    @Column(nullable = false, length = 2000, name = "description")
    private String description;

    @Column(nullable = false, length = 200, name = "author")
    private String author;

    @Column(nullable = false, name = "isBorrowed")
    private boolean isBorrowed;
}
