package ro.ubb.bookstore.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ro.ubb.bookstore.core.model.Book;
import ro.ubb.bookstore.core.model.validators.BookStoreException;
import ro.ubb.bookstore.core.model.validators.BookValidator;
import ro.ubb.bookstore.web.converter.BookConverter;
import ro.ubb.bookstore.web.dto.BookDto;
import ro.ubb.bookstore.web.dto.BooksDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class BookController {
    private static final String URL = "http://localhost:8081/api";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BookValidator bookValidator;

    @Autowired
    private BookConverter bookConverter;


    public void addBook(Book book) {
        bookValidator.validate(book);

        restTemplate.postForObject(
                URL + "/books",
                bookConverter.convertModelToDto(book),
                BookDto.class
        );
    }

    public void deleteBook(Long id) {
        try {
            restTemplate.delete(
                    URL + "/books/{id}",
                    id
            );
        }
        catch (HttpServerErrorException exception) {
            throw new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(id) + " does not exist.");
        }
    }

    public void updateBook(Book book) {
        bookValidator.validate(book);

        try{
            restTemplate.put(
                    URL + "/books/{id}",
                    bookConverter.convertModelToDto(book),
                    book.getId()
            );
        }
        catch (HttpServerErrorException exception) {
            throw new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(book.getId()) + " does not exist.");
        }
    }

    public Set<Book> getAllBooks() {
        BooksDto dtos = restTemplate.getForObject(
                URL + "/books",
                BooksDto.class
        );
        return bookConverter.convertDtosToModels(dtos.getBooks());
    }

    public Set<Book> getBooksByAuthor(String author) {
        Set<Book> books = this.getAllBooks();

        return books.stream()
                .filter(book -> author.equals(book.getAuthor()))
                .collect(Collectors.toSet());
    }
}
