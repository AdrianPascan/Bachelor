package ro.ubb.bookstore.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ro.ubb.bookstore.core.model.Book;
import ro.ubb.bookstore.core.model.Customer;
import ro.ubb.bookstore.core.model.validators.BookStoreException;
import ro.ubb.bookstore.core.model.validators.BookValidator;
import ro.ubb.bookstore.core.model.validators.CustomerValidator;
import ro.ubb.bookstore.web.converter.BookConverter;
import ro.ubb.bookstore.web.converter.CustomerConverter;
import ro.ubb.bookstore.web.dto.BookDto;
import ro.ubb.bookstore.web.dto.BooksDto;
import ro.ubb.bookstore.web.dto.CustomerDto;
import ro.ubb.bookstore.web.dto.CustomersDto;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class CustomerController {
    private static final String URL = "http://localhost:8081/api";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomerValidator customerValidator;

    @Autowired
    private CustomerConverter customerConverter;


    public void addCustomer(Customer customer) {
        customerValidator.validate(customer);

        restTemplate.postForObject(
                URL + "/customers",
                customerConverter.convertModelToDto(customer),
                CustomerDto.class
        );
    }

    public void deleteCustomer(Long id) {
        try {
            restTemplate.delete(
                    URL + "/customers/{id}",
                    id
            );
        }
        catch (HttpServerErrorException exception) {
            throw new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(id) + " does not exist.");
        }
    }

    public void updateCustomer(Customer customer) {
        customerValidator.validate(customer);

        try{
            restTemplate.put(
                    URL + "/customers/{id}",
                    customerConverter.convertModelToDto(customer),
                    customer.getId()
            );
        }
        catch (HttpServerErrorException exception) {
            throw new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(customer.getId()) + " does not exist.");
        }
    }

    public Set<Customer> getAllCustomers() {
        CustomersDto dtos = restTemplate.getForObject(
                URL + "/customers",
                CustomersDto.class
        );
        return customerConverter.convertDtosToModels(dtos.getCustomers());
    }

    public Set<Customer> getCustomersByAge(int age) {
        Set<Customer> customers = this.getAllCustomers();

        return customers.stream()
                .filter(customer -> age == customer.getAge())
                .collect(Collectors.toSet());
    }
}
