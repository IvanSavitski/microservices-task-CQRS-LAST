package test.task.bookcommandservice.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.task.bookcommandservice.command.entity.BookCommand;

import java.util.Optional;

@Repository
public interface BookCommandRepository extends JpaRepository<BookCommand, String> {
    Optional<BookCommand> findById(Long id);
    void deleteById(long id);
}
