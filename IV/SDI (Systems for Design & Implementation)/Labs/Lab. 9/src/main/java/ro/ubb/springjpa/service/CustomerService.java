package ro.ubb.springjpa.service;

import ro.ubb.springjpa.model.BaseEntity;
import ro.ubb.springjpa.model.Customer;

import java.io.Serializable;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    void saveCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomerById(Long id);
    List<Customer> getCustomersByAge(int age);
}
