package test.task.bookqueryservice.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.task.bookqueryservice.query.entity.BookQuery;

import java.util.Optional;

@Repository
public interface BookQueryRepository extends JpaRepository<BookQuery, String> {
    Optional<BookQuery> findById(Long id);
    Optional<BookQuery> findByIsbn(String isbn);

    void deleteById(long id);
}
