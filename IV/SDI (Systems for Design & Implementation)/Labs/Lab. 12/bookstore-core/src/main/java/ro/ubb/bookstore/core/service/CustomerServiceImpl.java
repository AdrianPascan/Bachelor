package ro.ubb.bookstore.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.bookstore.core.model.BaseEntity;
import ro.ubb.bookstore.core.model.Book;
import ro.ubb.bookstore.core.model.Customer;
import ro.ubb.bookstore.core.model.CustomerBook;
import ro.ubb.bookstore.core.model.validators.BookStoreException;
import ro.ubb.bookstore.core.repository.BookRepository;
import ro.ubb.bookstore.core.repository.CustomerRepository;

import javax.validation.Valid;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    public static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Customer getCustomer(Long customerId) {
        log.trace("getCustomer --- method entered: customerId={}", customerId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(customerId) + " does not exist."));

        log.trace("getCustomer --- method finished: customer={}", customer);
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        log.trace("getAllCustomers --- method entered");

        List<Customer> customers = customerRepository.findAll();

        log.trace("getAllCustomers --- method finished: customers={}", customers);
        return customers;
    }

    @Override
    public Customer saveCustomer(@Valid Customer customer) {
        log.trace("saveCustomer --- method entered: customer={}", customer);

        Customer savedCustomer = customerRepository.save(customer);

        log.trace("saveCustomer --- method finished: customer={}", customer);
        return savedCustomer;
    }

    @Override
    @Transactional
    public Customer updateCustomer(@Valid Customer customer) {
        log.trace("updateCustomer --- method entered: customer={}", customer);

        Long customerId = customer.getId();

        Customer savedCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(customer.getId()) + " does not exist."));
        savedCustomer.setFirstName(customer.getFirstName());
        savedCustomer.setLastName(customer.getLastName());
        savedCustomer.setAge(customer.getAge());
        if (! (customer.getCustomerBooks().isEmpty() )) {
            savedCustomer.setCustomerBooks(customer.getCustomerBooks());
        }

        bookRepository.findAll().stream()
                .map(Book::getCustomerBooks)
                .forEach(customerBooks ->
                        customerBooks.stream()
                                .filter(cb -> cb.getCustomer().getId() == customerId)
                                .forEach(cb -> cb.setCustomer(savedCustomer))
                );
        customerRepository.findAll().stream()
                .map(Customer::getCustomerBooks)
                .forEach(customerBooks ->
                        customerBooks.stream()
                                .filter(cb -> cb.getCustomer().getId() == customerId)
                                .forEach(cb -> cb.setCustomer(savedCustomer))
                );

        log.trace("updateCustomer --- method finished: customer={}", savedCustomer);
        return savedCustomer;
    }

    @Override
    @Transactional
    public void deleteCustomer(Long customerId) {
        log.trace("deleteCustomer --- method entered: customerId={}", customerId);

        customerRepository.findById(customerId)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(customerId) + " does not exist."));

        bookRepository.findAll().stream()
                .map(Book::getCustomerBooks)
                .forEach(customerBooks ->
                        customerBooks.removeIf(cb -> cb.getCustomer().getId() == customerId));
        customerRepository.findAll().stream()
                .map(Customer::getCustomerBooks)
                .forEach(customerBooks ->
                        customerBooks.removeIf(cb -> cb.getCustomer().getId() == customerId));

        customerRepository.deleteById(customerId);

        log.trace("deleteCustomer --- method finished");
    }
}
