package test.task.librarycommandservice.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.task.librarycommandservice.command.entity.LibraryCommand;

//import test.task.librarycommandservice.command.entity.LibraryCommand;

@Repository
public interface LibraryCommandRepository extends JpaRepository<LibraryCommand, Long> {
}

