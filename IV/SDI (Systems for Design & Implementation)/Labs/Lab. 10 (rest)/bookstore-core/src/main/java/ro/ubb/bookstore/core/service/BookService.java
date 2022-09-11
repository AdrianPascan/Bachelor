package ro.ubb.bookstore.core.service;

import ro.ubb.bookstore.core.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book saveBook(Book book);
    Book updateBook(Long id, Book book);
    void deleteBookById(Long id);
}
