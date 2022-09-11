package ro.ubb.springjpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.springjpa.model.Book;
import ro.ubb.springjpa.model.Pair;
import ro.ubb.springjpa.model.Transaction;
import ro.ubb.springjpa.model.validators.BookStoreException;
import ro.ubb.springjpa.model.validators.TransactionValidator;
import ro.ubb.springjpa.repository.BookRepository;
import ro.ubb.springjpa.repository.CustomerRepository;
import ro.ubb.springjpa.repository.TransactionRepository;

import javax.transaction.Transactional;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TransactionServiceImpl implements TransactionService {
    public static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionValidator transactionValidator;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public List<Transaction> getAllTransactions() {
        log.trace("getAllTransactions - method entered");

        List<Transaction> transactions = transactionRepository.findAll();

        log.trace("getAllTransactions - method finished");

        return transactions;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        log.trace("saveTransaction - method entered: transaction={}", transaction);

        transactionValidator.validate(transaction);

        customerRepository.findById(transaction.getCustomerId())
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(transaction.getCustomerId()) + " does not exist."));
        bookRepository.findById(transaction.getBookId())
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(transaction.getBookId()) + " does not exist."));

        transactionRepository.save(transaction);

        log.trace("saveTransaction - method finished");
    }

    @Override
    @Transactional
    public void updateTransaction(Transaction transaction) {
        log.trace("updateTransaction - method entered: transaction={}", transaction);

        transactionValidator.validate(transaction);
        transactionRepository.findById(transaction.getId())
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(transaction.getId()) + " does not exist."));
        customerRepository.findById(transaction.getCustomerId())
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(transaction.getCustomerId()) + " does not exist."));
        bookRepository.findById(transaction.getBookId())
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(transaction.getBookId()) + " does not exist."));

        transactionRepository.findById(transaction.getId())
                .ifPresent(t -> {
                    t.setCustomerId(transaction.getCustomerId());
                    t.setBookId(transaction.getBookId());

                    log.debug("updateTransaction - updated: transaction={}", t);
                });

        log.trace("updateTransaction - method finished");
    }

    @Override
    public void deleteTransactionById(Long id) {
        log.trace("deleteTransactionById - method entered: id={}", id);

        transactionRepository.findById(id)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(id) + " does not exist."));

        transactionRepository.deleteById(id);

        log.trace("deleteTransactionById - method finished");
    }

    @Override
    public List<Pair<Long, Book>> getTopBoughtBooks(int noOfBooks) {
        log.trace("getTopBoughtBooks - method entered: noOfBooks={}", noOfBooks);

        List<Pair<Long, Book>> result =
                bookRepository.findAll().stream()
                .map(book -> (Pair<Long, Book>) new Pair<Long, Book>(
                        transactionRepository.findAll().stream().filter(transaction -> transaction.getBookId().equals(book.getId())).count(),
                        book
                ))
                .sorted((pair, pair2) -> -Long.compare(pair.getFirst(), pair2.getFirst()))
                .limit(noOfBooks)
                .collect(Collectors.toList());

        log.trace("getTopBoughtBooks - method finished");

        return result;
    }
}
