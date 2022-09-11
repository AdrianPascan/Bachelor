package ro.ubb.bookstore.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.ubb.bookstore.core.model.validators.BookStoreException;
import ro.ubb.bookstore.core.model.validators.ValidatorException;
import ro.ubb.bookstore.core.service.BookService;
import ro.ubb.bookstore.web.converter.BookConverter;
import ro.ubb.bookstore.web.dto.BookDto;
import ro.ubb.bookstore.web.dto.BooksDto;

@RestController
public class BookController {
    public static final Logger log= LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private BookConverter bookConverter;


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    BooksDto getBooks() {
        log.trace("getBooks --- method entered");

        BooksDto dtos = new BooksDto(
                bookConverter.convertModelsToDtos(
                        bookService.getAllBooks()
                )
        );

        log.trace("getBooks --- booksDto={}", dtos);
        log.trace("getBooks --- method finished");

        return dtos;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/books", method = RequestMethod.POST)
    BookDto saveBook(@RequestBody BookDto bookDto) {
        log.trace("saveBook --- method entered");

        BookDto dto = bookConverter.convertModelToDto(
                bookService.saveBook(
                        bookConverter.convertDtoToModel(bookDto)
                )
        );

        log.trace("saveBook --- customerDto={}", dto);
        log.trace("saveBook --- method finished");

        return dto;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    BookDto updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        log.trace("updateBook --- method entered");

        BookDto dto = bookConverter.convertModelToDto(
                bookService.updateBook(
                        id,
                        bookConverter.convertDtoToModel(bookDto)
                )
        );

        log.trace("updateBook --- bookDto={}", dto);
        log.trace("updateBook --- method finished");

        return dto;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteBook(@PathVariable Long id){
        log.trace("deleteBook --- method entered");

        bookService.deleteBookById(id);
        ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        log.trace("deleteBook --- responseEntity={}", responseEntity);
        log.trace("deleteBook --- method finished");

        return responseEntity;
    }
}
