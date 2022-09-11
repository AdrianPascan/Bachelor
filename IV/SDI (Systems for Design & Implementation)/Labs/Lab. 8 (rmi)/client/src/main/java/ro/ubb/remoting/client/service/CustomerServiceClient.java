package ro.ubb.remoting.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.remoting.common.CustomerService;
import ro.ubb.remoting.common.domain.Customer;
import ro.ubb.remoting.common.domain.validators.ValidatorException;

import java.util.List;

public class CustomerServiceClient implements CustomerService {
    @Autowired
    private CustomerService customerService;

    @Override
    public void addCustomer(Customer customer) throws ValidatorException {
        customerService.addCustomer(customer);
    }

    @Override
    public void deleteCustomer(Long ID) throws IllegalArgumentException {
        customerService.deleteCustomer(ID);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerService.updateCustomer(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @Override
    public List<Customer> getCustomersByAge(int age) {
        return customerService.getCustomersByAge(age);
    }
}
