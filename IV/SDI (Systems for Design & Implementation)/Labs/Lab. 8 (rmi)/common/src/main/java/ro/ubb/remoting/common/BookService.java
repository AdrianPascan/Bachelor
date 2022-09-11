package ro.ubb.remoting.common;

import ro.ubb.remoting.common.domain.Book;
import ro.ubb.remoting.common.domain.Customer;
import ro.ubb.remoting.common.domain.validators.ValidatorException;

import java.util.List;

public interface BookService {
    void addBook(Book book) throws ValidatorException;
    void deleteBook(Long ID);
    void updateBook(Book book);
    List<Book> getAllBooks();
    List<Book> getBooksByAuthor(String author);
}
