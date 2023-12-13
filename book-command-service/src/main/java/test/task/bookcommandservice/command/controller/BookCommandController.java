package test.task.bookcommandservice.command.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.task.bookcommandservice.command.dto.BookCommandEvent;
import test.task.bookcommandservice.command.entity.BookCommand;
import test.task.bookcommandservice.command.service.BookCommandServiceImpl;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/books/command")
@Slf4j
public class BookCommandController {

    private final BookCommandServiceImpl bookCommandServiceImpl;

    private final ModelMapper modelMapper;


    @Autowired
    public BookCommandController(BookCommandServiceImpl bookCommandServiceImpl, ModelMapper modelMapper) {
        this.bookCommandServiceImpl = bookCommandServiceImpl;
        this.modelMapper = modelMapper;
    }


    //http://localhost:8082/api/books/command/create/book
    @PostMapping("create/book")
    public ResponseEntity<BookCommandEvent> addBook(@RequestBody BookCommandEvent bookCommandEvent) {
        log.info("Creating Book...");
        BookCommand book = modelMapper.map(bookCommandEvent, BookCommand.class);
        BookCommand savedBook = bookCommandServiceImpl.addBook(book);
        BookCommandEvent savedBookDTO = modelMapper.map(savedBook, BookCommandEvent.class);

        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }


    //http://localhost:8082/api/books/command/create/books
    @PostMapping("create/books")
    public ResponseEntity<List<BookCommandEvent>> addBooks(@RequestBody List<BookCommandEvent> bookCommandEvent) {
        log.info("Creating Books...");

        List<BookCommand> books = bookCommandEvent.stream()
                .map(bookDTO -> modelMapper.map(bookDTO, BookCommand.class))
                .collect(Collectors.toList());

        List<BookCommand> savedBooks = bookCommandServiceImpl.addBooks(books);

        List<BookCommandEvent> savedBookDTOs = savedBooks.stream()
                .map(book -> modelMapper.map(book, BookCommandEvent.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(savedBookDTOs, HttpStatus.CREATED);
    }


    //http://localhost:8082/api/books/command/update/{id}
    @PutMapping("update/{id}")
    public ResponseEntity<BookCommandEvent> updateBook(@PathVariable Long id, @RequestBody BookCommandEvent bookDTO) {
        BookCommand book = modelMapper.map(bookDTO, BookCommand.class);
        BookCommand updatedBook = bookCommandServiceImpl.updateBook(book, id);
        BookCommandEvent updatedBookDTO = modelMapper.map(updatedBook, BookCommandEvent.class);

        return new ResponseEntity<>(updatedBookDTO, HttpStatus.OK);
    }


    //http://localhost:8082/api/books/command/delete/{id}
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookCommandServiceImpl.deleteBook(id);
        return new ResponseEntity<String>("Book deleted successfully!.", HttpStatus.OK);
    }

}