package ro.ubb.bookstore.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.bookstore.core.model.BaseEntity;
import ro.ubb.bookstore.core.model.Customer;
import ro.ubb.bookstore.core.model.validators.BookStoreException;
import ro.ubb.bookstore.core.repository.CustomerRepository;
import ro.ubb.bookstore.core.repository.TransactionRepository;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    public static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public List<Customer> getAllCustomers() {
        log.trace("getAllCustomers --- method entered");

        List<Customer> customers = customerRepository.findAll();

        log.trace("getAllCustomers --- customers={}", customers);
        log.trace("getAllCustomers --- method finished");

        return customers;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        log.trace("saveCustomer --- method entered: customer={}", customer);

        Customer saved = customerRepository.save(customer);

        log.trace("saveCustomer --- saved: customer={}", saved);
        log.trace("saveCustomer --- method finished");

        return saved;
    }

    @Override
    @Transactional
    public Customer updateCustomer(Long id, Customer customer) {
        log.trace("updateCustomer --- method entered: id={}, customer={}", id, customer);

        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(customer.getId()) + " does not exist."));

        c.setFirstName(customer.getFirstName());
        c.setLastName(customer.getLastName());
        c.setAge(customer.getAge());

        log.debug("updateCustomer --- updated: customer={}", c);
        log.trace("updateCustomer --- method finished");

        return c;
    }

    @Override
    public void deleteCustomerById(Long id) {
        log.trace("deleteCustomerById --- mehod entered: id={}", id);

        customerRepository.findById(id)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(id) + " does not exist."));

        customerRepository.deleteById(id);

        transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getCustomerId().equals(id))
                .map(BaseEntity::getId)
                .forEach(transactionId -> transactionRepository.deleteById(transactionId));

        log.trace("deleteCustomerById --- mehod finished");
    }
}
