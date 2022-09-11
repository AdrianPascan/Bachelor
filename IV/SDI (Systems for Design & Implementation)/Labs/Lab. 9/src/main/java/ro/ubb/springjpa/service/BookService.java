package ro.ubb.springjpa.service;

import ro.ubb.springjpa.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    void saveBook(Book book);
    void updateBook(Book book);
    void deleteBookById(Long id);
    List<Book> getBooksByAuthor(String author);
}
