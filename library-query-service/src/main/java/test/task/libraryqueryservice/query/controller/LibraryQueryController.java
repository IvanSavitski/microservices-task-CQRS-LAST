package test.task.libraryqueryservice.query.controller;

import test.task.libraryqueryservice.query.service.LibraryQueryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.task.bookqueryservice.query.entity.BookQuery;
//import test.task.libraryqueryservice.query.service.LibraryQueryServiceImpl;

import java.util.List;


@RestController
@RequestMapping("/api/library/query")
public class LibraryQueryController {
    private final LibraryQueryServiceImpl libraryQueryServiceImpl;

    @Autowired
    public LibraryQueryController(LibraryQueryServiceImpl libraryQueryServiceImpl) {
        this.libraryQueryServiceImpl = libraryQueryServiceImpl;
    }


    //http://localhost:8081/api/library/query/get/getAvailable
    @GetMapping("get/getAvailable")
    public ResponseEntity<List<BookQuery>> getAvailableBooks() {
        List<BookQuery> books = libraryQueryServiceImpl.getAvailableBooks();
        return ResponseEntity.ok(books);
    }



    //http://localhost:8081/api/library/query/get/getAllBorrowed
    @GetMapping("get/getAllBorrowed")
    public ResponseEntity<List<BookQuery>> getAllBorrowedBooks() {
        List<BookQuery> books = libraryQueryServiceImpl.getAllBorrowedBooks();
        return ResponseEntity.ok(books);
    }
}

