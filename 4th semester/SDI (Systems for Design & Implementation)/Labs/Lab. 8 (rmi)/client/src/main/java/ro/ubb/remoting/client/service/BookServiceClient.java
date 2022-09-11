package ro.ubb.remoting.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.remoting.common.BookService;
import ro.ubb.remoting.common.Service;

import java.util.List;
import ro.ubb.remoting.common.domain.Book;

import ro.ubb.remoting.common.domain.validators.ValidatorException;

public class BookServiceClient implements BookService {
    @Autowired
    private BookService bookService;

    @Override
    public void addBook(Book book) throws ValidatorException {
        bookService.addBook(book);
    }

    @Override
    public void deleteBook(Long ID) {
        bookService.deleteBook(ID);
    }

    @Override
    public void updateBook(Book book) {
        bookService.updateBook(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookService.getBooksByAuthor(author);
    }
}
