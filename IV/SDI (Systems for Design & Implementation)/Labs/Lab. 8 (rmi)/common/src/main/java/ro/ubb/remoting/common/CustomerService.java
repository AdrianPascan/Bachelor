package ro.ubb.remoting.common;

import ro.ubb.remoting.common.domain.Customer;
import ro.ubb.remoting.common.domain.validators.ValidatorException;

import java.util.List;

public interface CustomerService {
    void addCustomer(Customer customer) throws ValidatorException;
    void deleteCustomer(Long ID) throws IllegalArgumentException;
    void updateCustomer(Customer customer);
    List<Customer> getAllCustomers();
    List<Customer> getCustomersByAge(int age);
}
