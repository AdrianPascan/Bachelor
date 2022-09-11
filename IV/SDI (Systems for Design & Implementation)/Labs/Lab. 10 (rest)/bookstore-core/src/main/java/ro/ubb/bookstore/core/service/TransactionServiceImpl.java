package ro.ubb.bookstore.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.bookstore.core.model.Transaction;
import ro.ubb.bookstore.core.model.validators.BookStoreException;
import ro.ubb.bookstore.core.repository.BookRepository;
import ro.ubb.bookstore.core.repository.CustomerRepository;
import ro.ubb.bookstore.core.repository.TransactionRepository;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    public static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public List<Transaction> getAllTransactions() {
        log.trace("getAllTransactions --- method entered");

        List<Transaction> transactions = transactionRepository.findAll();

        log.trace("getAllTransactions --- transactions={}", transactions);
        log.trace("getAllTransactions --- method finished");

        return transactions;
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        log.trace("saveTransaction --- method entered: transaction={}", transaction);

        customerRepository.findById(transaction.getCustomerId())
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(transaction.getCustomerId()) + " does not exist."));
        bookRepository.findById(transaction.getBookId())
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(transaction.getBookId()) + " does not exist."));

        Transaction saved = transactionRepository.save(transaction);

        log.trace("saveTransaction --- saved: transaction={}", saved);
        log.trace("saveTransaction --- method finished");

        return saved;
    }

    @Override
    @Transactional
    public Transaction updateTransaction(Long id, Transaction transaction) {
        log.trace("updateTransaction --- method entered: id={}, transaction={}", id, transaction);

        Transaction t = transactionRepository.findById(transaction.getId())
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Transaction with id=" + Long.toString(transaction.getId()) + " does not exist."));
        customerRepository.findById(transaction.getCustomerId())
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(transaction.getCustomerId()) + " does not exist."));
        bookRepository.findById(transaction.getBookId())
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(transaction.getBookId()) + " does not exist."));

        t.setCustomerId(transaction.getCustomerId());
        t.setBookId(transaction.getBookId());

        log.debug("updateTransaction --- updated: transaction={}", t);
        log.trace("updateTransaction --- method finished");

        return t;
    }

    @Override
    public void deleteTransactionById(Long id) {
        log.trace("deleteTransactionById --- method entered: id={}", id);

        transactionRepository.findById(id)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(id) + " does not exist."));

        transactionRepository.deleteById(id);

        log.trace("deleteTransactionById --- method finished");
    }
}
