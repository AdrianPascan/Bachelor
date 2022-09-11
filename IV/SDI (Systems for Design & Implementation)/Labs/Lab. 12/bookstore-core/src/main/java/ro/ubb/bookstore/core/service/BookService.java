package ro.ubb.bookstore.core.service;

import ro.ubb.bookstore.core.model.Book;
import ro.ubb.bookstore.core.model.CustomerBook;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    Book getBook(Long bookId);
    List<Book> getAllBooks();
    Book saveBook(Book book);
    Book updateBook(Book book);
    void deleteBook(Long bookId);
}
