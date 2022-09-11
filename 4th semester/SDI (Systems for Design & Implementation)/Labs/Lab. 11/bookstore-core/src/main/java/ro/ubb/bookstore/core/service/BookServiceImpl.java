package ro.ubb.bookstore.core.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.bookstore.core.model.Book;
import ro.ubb.bookstore.core.model.BaseEntity;
import ro.ubb.bookstore.core.model.validators.BookStoreException;
import ro.ubb.bookstore.core.repository.BookRepository;
import ro.ubb.bookstore.core.repository.TransactionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    public static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public List<Book> getAllBooks() {
        log.trace("getAllBooks --- method entered");

        List<Book> books = bookRepository.findAll();

        log.trace("getAllBooks --- books={}", books);
        log.trace("getAllBooks --- method finished");

        return books;
    }

    @Override
    public Book saveBook(Book book) {
        log.trace("saveBook --- method entered: book={}", book);

        Book saved = bookRepository.save(book);

        log.trace("saveBook --- saved: book={}", saved);
        log.trace("saveBook --- method finished");

        return saved;
    }

    @Override
    @Transactional
    public Book updateBook(Long id, Book book) {
        log.trace("updateBook --- method entered: id={}, book={}", id, book);

        Book b = bookRepository.findById(id)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(book.getId()) + " does not exist."));

        b.setIsbn(book.getIsbn());
        b.setTitle(book.getTitle());
        b.setAuthor(book.getAuthor());
        b.setPublishingHouse(book.getPublishingHouse());
        b.setYear(book.getYear());
        b.setPrice(book.getPrice());

        log.debug("updateBook --- updated: book={}", b);
        log.trace("updateBook --- method finished");

        return b;
    }

    @Override
    public void deleteBookById(Long id) {
        log.trace("deleteBookById --- method entered: id={}", id);

        bookRepository.findById(id)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(id) + " does not exist."));

        bookRepository.deleteById(id);

        transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getBookId().equals(id))
                .map(BaseEntity::getId)
                .forEach(transactionId -> transactionRepository.deleteById(transactionId));

        log.trace("deleteBookById --- method finished");
    }
}
