package ro.ubb.bookstore.core.service;

import ro.ubb.bookstore.core.model.Customer;
import ro.ubb.bookstore.core.model.CustomerBook;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface CustomerService {
    Customer getCustomer(Long customerId);
    List<Customer> getAllCustomers();
    Customer saveCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    void deleteCustomer(Long customerId);
}
