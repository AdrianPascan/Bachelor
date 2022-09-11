package ro.ubb.remoting.server.service;

import ro.ubb.remoting.common.BookService;
import ro.ubb.remoting.common.domain.BaseEntity;
import ro.ubb.remoting.common.domain.Book;
import ro.ubb.remoting.common.domain.Transaction;
import ro.ubb.remoting.common.domain.validators.ValidatorException;
import ro.ubb.remoting.server.repository.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class BookServerService implements BookService {
    private Repository<Long, Book> bookRepo;
    private Repository<Long, Transaction> transactionRepo;

    public BookServerService(Repository<Long, Book> bookRepo, Repository<Long, Transaction> transactionRepo) {
        this.bookRepo = bookRepo;
        this.transactionRepo = transactionRepo;
    }

    public void addBook(Book book) throws ValidatorException {
        bookRepo.save(book);
    }

    public void deleteBook(Long ID) {
        deleteTransactionsByBookID(ID);
        bookRepo.deleteById(ID);
    }

    public void updateBook(Book book) {
        bookRepo.update(book);
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepo.findAll().stream()
                .filter(book -> author.equals(book.getAuthor()))
                .collect(Collectors.toList());
    }

    private void deleteTransactionsByBookID(Long bookID) {
        List<Long> transactionIDs = transactionRepo.findAll().stream()
                .filter(transaction -> transaction.getBookId() == bookID)
                .map(BaseEntity::getId).collect(Collectors.toList());

        transactionIDs.forEach(ID -> transactionRepo.deleteById(ID));
    }
}
