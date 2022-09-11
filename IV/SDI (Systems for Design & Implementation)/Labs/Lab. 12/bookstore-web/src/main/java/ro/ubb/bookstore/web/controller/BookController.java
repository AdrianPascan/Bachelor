package ro.ubb.bookstore.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ro.ubb.bookstore.core.service.BookService;
import ro.ubb.bookstore.web.converter.BookConverter;
import ro.ubb.bookstore.web.dto.BookDto;

import java.util.Set;

@RestController
public class BookController {
    public static final Logger log= LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private BookConverter bookConverter;


    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public Set<BookDto> getBooks() {
        log.trace("getBooks --- method entered");

        Set<BookDto> bookDtos = (Set<BookDto>) bookConverter.convertModelsToDtos(bookService.getAllBooks());

        log.trace("getBooks --- method finished: bookDtos={}", bookDtos);
        return bookDtos;
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public BookDto saveBook(@RequestBody final BookDto bookDto) {
        log.trace("saveBook --- method entered: bookDto={}", bookDto);

        BookDto savedBookDto = bookConverter.convertModelToDto(
                bookService.saveBook(bookConverter.convertDtoToModel(bookDto)));

        log.trace("saveBook --- method finished: bookDto={}", savedBookDto);

        return savedBookDto;
    }

    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.PUT)
    @Transactional
    public BookDto updateBook(@PathVariable final Long bookId,
                              @RequestBody final BookDto bookDto) {
        log.trace("updateBook --- method entered: bookId={}, bookDto={}", bookId, bookDto);

        bookDto.setId(bookId);
        BookDto updatedBookDto = bookConverter.convertModelToDto(
                bookService.updateBook(bookConverter.convertDtoToModel(bookDto)));

        log.trace("updateBook --- method finished: bookDto={}", updatedBookDto);
        return updatedBookDto;
    }

    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<?> deleteBook(@PathVariable final Long bookId){
        log.trace("deleteBook --- method entered: bookId={}", bookId);

        bookService.deleteBook(bookId);
        ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        log.trace("deleteBook --- method finished: responseEntity={}", responseEntity);
        return responseEntity;
    }
}
