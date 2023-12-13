package test.task.libraryqueryservice.query.repository;

import test.task.libraryqueryservice.query.entity.LibraryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryQueryRepository  extends JpaRepository<LibraryQuery, Long> {
}
