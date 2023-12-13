package test.task.librarycommandservice.command.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.task.librarycommandservice.command.service.LibraryCommandServiceImpl;

@RestController
@RequestMapping("/api/library/command")
public class LibraryCommandController {
    private final LibraryCommandServiceImpl libraryCommandServiceImpl;

    @Autowired
    public LibraryCommandController(LibraryCommandServiceImpl libraryCommandServiceImpl) {
        this.libraryCommandServiceImpl = libraryCommandServiceImpl;
    }

    //http://localhost:8081/api/library/command/borrowBook/{bookId}
    @PostMapping("/borrowBook/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable long bookId) {
        libraryCommandServiceImpl.borrowBook(bookId);
        return ResponseEntity.ok("Book borrowed successfully");
    }


    //http://localhost:8081/api/library/command/addBookToLibrary/{bookId}
    @PostMapping("/addBookToLibrary/{bookId}")
    public ResponseEntity<String> addBookToLibrary(@PathVariable long bookId) {
        libraryCommandServiceImpl.addBookToLibrary(bookId);
        return ResponseEntity.ok("Book added to library successfully");
    }


    //http://localhost:8081/api/library/command/update/books/{bookId}/library/{libraryBookId}/return
    @PutMapping("update/books/{bookId}/library/{libraryBookId}/return")
    public ResponseEntity<String> returnBook(@PathVariable long bookId, @PathVariable long libraryBookId) {
        libraryCommandServiceImpl.returnBook(bookId, libraryBookId);
        return ResponseEntity.ok("Book returned successfully");
    }


    //http://localhost:8081/api/library/command/delete/{bookId}
    @DeleteMapping("delete/{bookId}")
    public ResponseEntity<String> deleteBookFromLibrary(@PathVariable Long bookId) {
        libraryCommandServiceImpl.deleteBook(bookId);
        return new ResponseEntity<String>("Book deleted from library successfully!.", HttpStatus.OK);
    }
}
