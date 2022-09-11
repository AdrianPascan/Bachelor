package ro.ubb.springjpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.springjpa.model.BaseEntity;
import ro.ubb.springjpa.model.Book;
import ro.ubb.springjpa.model.validators.BookStoreException;
import ro.ubb.springjpa.model.validators.BookValidator;
import ro.ubb.springjpa.repository.BookRepository;
import ro.ubb.springjpa.repository.TransactionRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    public static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookValidator bookValidator;

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public List<Book> getAllBooks() {
        log.trace("getAllBooks - method entered");

        List<Book> books = bookRepository.findAll();

        log.trace("getAllBooks - method finished");

        return books;
    }

    @Override
    public void saveBook(Book book) {
        log.trace("saveBook - method entered: book={}", book);

        bookValidator.validate(book);
        bookRepository.save(book);

        log.trace("saveBook - method finished");
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        log.trace("updateBook - method entered: book={}", book);

        bookValidator.validate(book);
        bookRepository.findById(book.getId())
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(book.getId()) + " does not exist."));

        bookRepository.findById(book.getId())
                .ifPresent(b -> {
                    b.setIsbn(book.getIsbn());
                    b.setTitle(book.getTitle());
                    b.setAuthor(book.getAuthor());
                    b.setPublishingHouse(book.getPublishingHouse());
                    b.setYear(book.getYear());
                    b.setPrice(book.getPrice());

                    log.debug("updateBook - updated: book={}", b);
                });

        log.trace("updateBook - method finished");
    }

    @Override
    public void deleteBookById(Long id) {
        log.trace("deleteBookById - method entered: id={}", id);

        bookRepository.findById(id)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(id) + " does not exist."));

        bookRepository.deleteById(id);

        transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getBookId().equals(id))
                .map(BaseEntity::getId)
                .forEach(transactionId -> transactionRepository.deleteById(transactionId));

        log.trace("deleteBookById - method finished");
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        log.trace("getBooksByAuthor - method entered: author={}", author);

        List<Book> books = bookRepository.findAll().stream()
                .filter(book -> author.equals(book.getAuthor()))
                .collect(Collectors.toList());

        log.trace("getBooksByAuthor - method finished");

        return books;
    }
}
