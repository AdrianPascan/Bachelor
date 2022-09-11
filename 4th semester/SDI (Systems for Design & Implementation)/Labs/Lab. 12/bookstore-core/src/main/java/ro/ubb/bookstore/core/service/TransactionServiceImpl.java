package ro.ubb.bookstore.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.bookstore.core.model.Book;
import ro.ubb.bookstore.core.model.Customer;
import ro.ubb.bookstore.core.model.CustomerBook;
import ro.ubb.bookstore.core.model.validators.BookStoreException;
import ro.ubb.bookstore.core.repository.BookRepository;
import ro.ubb.bookstore.core.repository.CustomerRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    public static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;


    @Override
    public CustomerBook getTransaction(Long customerId, Long bookId) {
        log.trace("getTransaction --- method entered");

        Customer savedCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(customerId) + " does not exist."));
        bookRepository.findById(bookId)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(bookId) + " does not exist."));
        CustomerBook savedCustomerBook = savedCustomer.getCustomerBooks().stream()
                .filter(cb -> cb.getBook().getId() == bookId)
                .findFirst()
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Transaction with customerId=" + Long.toString(customerId) + " and bookId=" + Long.toString(bookId) + " does not exist."));

        log.trace("getTransaction --- method finished: customerBook={}", savedCustomerBook);
        return savedCustomerBook;
    }

    @Override
    public List<CustomerBook> getAllTransactions() {
        log.trace("getAllTransactions --- method entered");

        List<CustomerBook> customerBooks = new ArrayList<>(customerRepository.findAll().stream()
                .map(Customer::getCustomerBooks)
                .reduce(new HashSet<>(), (accumulatorSet, currentSet) -> {
                    accumulatorSet.addAll(currentSet);
                    return accumulatorSet;
                })
        );

        log.trace("getAllTransactions --- method finished: customerBooks={}", customerBooks);
        return customerBooks;
    }

    @Override
    @Transactional
    public CustomerBook saveTransaction(@Valid CustomerBook customerBook) {
        log.trace("saveTransaction --- method entered: customerBook={}", customerBook);

        Long customerId = customerBook.getCustomer().getId();
        Long bookId = customerBook.getBook().getId();

        Customer savedCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(customerId) + " does not exist."));
        Book savedBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(bookId) + " does not exist."));
        CustomerBook savedCustomerBook = new CustomerBook(savedCustomer, savedBook,
                customerBook.getQuantity());
        savedCustomer.getCustomerBooks().add(savedCustomerBook);
        savedBook.getCustomerBooks().add(savedCustomerBook);

        log.trace("saveTransaction --- method entered: customerBook={}", customerBook);
        return savedCustomerBook;
    }

    @Override
    @Transactional
    public CustomerBook updateTransaction(@Valid CustomerBook customerBook) {
        log.trace("updateTransaction --- method entered: customerBook={}", customerBook);

        Long customerId = customerBook.getCustomer().getId();
        Long bookId = customerBook.getBook().getId();

        Customer savedCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(customerId) + " does not exist."));
        Book savedBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(bookId) + " does not exist."));

        CustomerBook savedCustomerBook = savedCustomer.getCustomerBooks().stream()
                .filter(cb -> cb.getCustomer().getId() == customerId && cb.getBook().getId() == bookId)
                .findFirst()
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Transaction with customerId=" + Long.toString(customerId) + " and bookId=" + Long.toString(bookId) + " does not exist."));
        savedCustomerBook.setQuantity(customerBook.getQuantity());
        CustomerBook savedCustomerBook2 = savedBook.getCustomerBooks().stream()
                .filter(cb -> cb.getCustomer().getId() == customerId && cb.getBook().getId() == bookId)
                .findFirst()
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Transaction with customerId=" + Long.toString(customerId) + " and bookId=" + Long.toString(bookId) + " does not exist."));
        savedCustomerBook2.setQuantity(customerBook.getQuantity());

        log.trace("updateTransaction --- method finished: customerBook={}", savedCustomerBook);
        return savedCustomerBook;
    }

    @Override
    @Transactional
    public void deleteTransaction(Long customerId, Long bookId) {
        log.trace("deleteTransaction --- method entered: customerId={}, bookId={}", customerId, bookId);

        Customer savedCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(customerId) + " does not exist."));
        Book savedBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Book with id=" + Long.toString(bookId) + " does not exist."));
        savedCustomer.getCustomerBooks().stream()
                .filter(cb -> cb.getCustomer().getId() == customerId && cb.getBook().getId() == bookId)
                .findFirst()
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Transaction with customerId=" + Long.toString(customerId) + " and bookId=" + Long.toString(bookId) + " does not exist."));
        savedCustomer.getCustomerBooks().removeIf(cb -> cb.getCustomer().getId() == customerId && cb.getBook().getId() == bookId);
        savedBook.getCustomerBooks().removeIf(cb -> cb.getCustomer().getId() == customerId && cb.getBook().getId() == bookId);

        log.trace("deleteTransaction --- method finished");
    }
}
