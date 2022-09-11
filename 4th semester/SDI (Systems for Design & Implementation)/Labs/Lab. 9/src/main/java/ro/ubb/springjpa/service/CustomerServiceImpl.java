package ro.ubb.springjpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.springjpa.model.BaseEntity;
import ro.ubb.springjpa.model.Customer;
import ro.ubb.springjpa.model.validators.BookStoreException;
import ro.ubb.springjpa.model.validators.CustomerValidator;
import ro.ubb.springjpa.repository.CustomerRepository;
import ro.ubb.springjpa.repository.TransactionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    public static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerValidator customerValidator;

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public List<Customer> getAllCustomers() {
        log.trace("getAllCustomers - method entered");

        List<Customer> customers = customerRepository.findAll();

        log.trace("getAllCustomers - method finished");

        return customers;
    }

    @Override
    public void saveCustomer(Customer customer) {
        log.trace("saveCustomer - method entered: customer={}", customer);

        customerValidator.validate(customer);
        customerRepository.save(customer);

        log.trace("saveCustomer - method finished");
    }

    @Override
    @Transactional
    public void updateCustomer(Customer customer) {
        log.trace("updateCustomer - method entered: customer={}", customer);

        customerValidator.validate(customer);
        customerRepository.findById(customer.getId())
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(customer.getId()) + " does not exist."));

        customerRepository.findById(customer.getId())
                .ifPresent(c -> {
                    c.setFirstName(customer.getFirstName());
                    c.setLastName(customer.getLastName());
                    c.setAge(customer.getAge());

                    log.debug("updateCustomer - updated: customer={}", c);
                });

        log.trace("updateCustomer - method finished");
    }

    @Override
    public void deleteCustomerById(Long id) {
        log.trace("deleteCustomerById - mehod entered: id={}", id);

        customerRepository.findById(id)
                .orElseThrow(() -> new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(id) + " does not exist."));

        customerRepository.deleteById(id);

        transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getCustomerId().equals(id))
                .map(BaseEntity::getId)
                .forEach(transactionId -> transactionRepository.deleteById(transactionId));

        log.trace("deleteCustomerById - mehod finished");
    }

    @Override
    public List<Customer> getCustomersByAge(int age) {
        log.trace("getCustomersByAge - method entered: age={}", age);

        List<Customer> customers = customerRepository.findAll().stream()
                .filter(customer -> age == customer.getAge())
                .collect(Collectors.toList());

        log.trace("getCustomersByAge - method finished");

        return customers;
    }
}
