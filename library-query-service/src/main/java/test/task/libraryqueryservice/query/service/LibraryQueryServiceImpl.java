package test.task.libraryqueryservice.query.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;

import test.task.libraryqueryservice.query.dto.LibraryAndBookQueryKafkaEvent;
import test.task.libraryqueryservice.query.dto.LibraryQueryIdKafkaEvent;
import test.task.libraryqueryservice.query.dto.LibraryQueryKafkaEvent;
import test.task.libraryqueryservice.query.entity.LibraryQuery;
import test.task.libraryqueryservice.query.repository.LibraryQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import test.task.bookqueryservice.query.entity.BookQuery;
import test.task.bookqueryservice.query.repository.BookQueryRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LibraryQueryServiceImpl implements LibraryQueryService {
    private static final Long DAYS = 60L; //14L;
    private final LibraryQueryRepository libraryQueryRepository;
    private final BookQueryRepository bookQueryRepository;

    @Autowired
    public LibraryQueryServiceImpl(LibraryQueryRepository libraryQueryRepository, BookQueryRepository bookQueryRepository) {
        this.libraryQueryRepository = libraryQueryRepository;
        //this.webClient = webClientBuilder.build();
        this.bookQueryRepository = bookQueryRepository;
    }

    public List<BookQuery> getAvailableBooks() {
        List<LibraryQuery> libraryBooks = libraryQueryRepository.findAll();
        List<BookQuery> availableBooks = libraryBooks.stream()
                // .filter(libraryBook -> libraryBook.getReturnTime().isBefore(LocalDateTime.now()))
                .filter(libraryBook -> libraryBook.getBorrowedTime() == null && libraryBook.getReturnTime() == null)
                .map(LibraryQuery::getBook)
                .collect(Collectors.toList());

        return availableBooks;
    }

    public List<BookQuery> getAllBorrowedBooks() {
        List<LibraryQuery> libraryBooks = libraryQueryRepository.findAll();
        List<BookQuery> borrowedBooks = libraryBooks.stream()
                //.filter(libraryBook -> libraryBook.getReturnTime().isAfter(LocalDateTime.now()))
                .filter(libraryBook -> libraryBook.getBorrowedTime() != null && libraryBook.getReturnTime() != null)
                .map(LibraryQuery::getBook)
                .collect(Collectors.toList());

        return borrowedBooks;
    }
}
