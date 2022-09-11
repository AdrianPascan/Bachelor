package ro.ubb.bookstore.core.service;

import ro.ubb.bookstore.core.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer saveCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customer);
    void deleteCustomerById(Long id);
}
