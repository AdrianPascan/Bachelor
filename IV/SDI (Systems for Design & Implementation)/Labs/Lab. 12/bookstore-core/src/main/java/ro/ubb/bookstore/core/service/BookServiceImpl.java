package ro.ubb.bookstore.core.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.bookstore.core.model.Book;
import ro.ubb.bookstore.core.model.BaseEntity;
import ro.ubb.bookstore.core.model.Customer;
import ro.ubb.bookstore.core.model.CustomerBook;
import ro.ubb.bookstore.core.model.validators.BookStoreException;
import ro.ubb.bookstore.core.repository.BookRepository;
import ro.ubb.bookstore.core.repository.CustomerRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {
    public static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;


    @Override
    public Book getBook(Long bookId) {
        log.trace("getBook --- method entered: bookId={}", bookId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(bookId) + " does not exist."));

        log.trace("getBook --- method finished: book={}", book);
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        log.trace("getAllBooks --- method entered");

        List<Book> books = bookRepository.findAll();

        log.trace("getAllBooks --- method finished: books={}", books);
        return books;
    }

    @Override
    public Book saveBook(@Valid Book book) {
        log.trace("saveBook --- method entered: book={}", book);

        Book savedBook = bookRepository.save(book);

        log.trace("saveBook --- method finished: book={}", book);

        return savedBook;
    }

    @Override
    @Transactional
    public Book updateBook(@Valid Book book) {
        log.trace("updateBook --- method entered: book={}", book);

        Long bookId = book.getId();

        Book savedBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(bookId) + " does not exist."));
        savedBook.setIsbn(book.getIsbn());
        savedBook.setTitle(book.getTitle());
        savedBook.setAuthor(book.getAuthor());
        savedBook.setPrice(book.getPrice());
        if (! (book.getCustomerBooks().isEmpty()) ) {
            savedBook.setCustomerBooks(book.getCustomerBooks());
        }

        bookRepository.findAll().stream()
                .map(Book::getCustomerBooks)
                .forEach(customerBooks ->
                        customerBooks.stream()
                        .filter(cb -> cb.getBook().getId() == bookId)
                        .forEach(cb -> cb.setBook(savedBook))
                );
        customerRepository.findAll().stream()
                .map(Customer::getCustomerBooks)
                .forEach(customerBooks ->
                        customerBooks.stream()
                        .filter(cb -> cb.getBook().getId() == bookId)
                        .forEach(cb -> cb.setBook(savedBook))
                );

        log.trace("updateBook --- method finished: book={}", savedBook);
        return savedBook;
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        log.trace("deleteBook --- method entered: bookId={}", bookId);

        bookRepository.findById(bookId)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(bookId) + " does not exist."));

        bookRepository.findAll().stream()
                .map(Book::getCustomerBooks)
                .forEach(customerBooks ->
                        customerBooks.removeIf(cb -> cb.getBook().getId() == bookId));
        customerRepository.findAll().stream()
                .map(Customer::getCustomerBooks)
                .forEach(customerBooks ->
                        customerBooks.removeIf(cb -> cb.getBook().getId() == bookId));

        bookRepository.deleteById(bookId);

        log.trace("deleteBook --- method finished");
    }
}
