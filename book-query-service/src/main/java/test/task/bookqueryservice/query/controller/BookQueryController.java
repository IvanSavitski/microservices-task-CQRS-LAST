package test.task.bookqueryservice.query.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.task.bookqueryservice.query.dto.BookQueryEvent;
import test.task.bookqueryservice.query.entity.BookQuery;
import test.task.bookqueryservice.query.service.BookQueryServiceImpl;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books/query")
@Slf4j
public class BookQueryController {
    private final BookQueryServiceImpl bookQueryServiceImpl;
    private final ModelMapper modelMapper;


    @Autowired
    public BookQueryController(BookQueryServiceImpl bookQueryServiceImpl, ModelMapper modelMapper) {
        this.bookQueryServiceImpl = bookQueryServiceImpl;
        this.modelMapper = modelMapper;
    }



    // http://localhost:8082/api/books/query/get/getAll
    @GetMapping("get/getAll")
    public List<BookQueryEvent> getAllBooks() {
        List<BookQuery> books = bookQueryServiceImpl.getAllBooks();
        return books.stream()
                .map(bookQuery -> modelMapper.map(bookQuery, BookQueryEvent.class))
                .collect(Collectors.toList());
    }



    // http://localhost:8082/api/books/query/get/getById/{id}
    @GetMapping("get/getById/{id}")
    public ResponseEntity<BookQueryEvent> getBookById(@PathVariable(value = "id") Long id) {
        return bookQueryServiceImpl.getBookById(id)
                .map(bookQuery -> modelMapper.map(bookQuery, BookQueryEvent.class))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    // http://localhost:8082/api/books/query/get/getByIsbn/{isbn}
    @GetMapping("get/getByIsbn/{isbn}")
    public ResponseEntity<BookQueryEvent> getBookByIsbn(@PathVariable (value = "isbn") String isbn) {
        return bookQueryServiceImpl.getBookByIsbn(isbn)
                .map(book -> modelMapper.map(book, BookQueryEvent.class))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
