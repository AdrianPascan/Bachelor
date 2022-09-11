package ro.ubb.remoting.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.remoting.common.Service;

import java.util.List;
import ro.ubb.remoting.common.domain.Book;
import ro.ubb.remoting.common.domain.Customer;
import ro.ubb.remoting.common.domain.Transaction;
import ro.ubb.remoting.common.domain.validators.ValidatorException;

import java.util.Map;
import java.util.Optional;

/**
 * Created by radu.
 */
public class ServiceClient implements Service {
    @Autowired
    private Service service;

    @Override
    public void addCustomer(Customer customer) throws ValidatorException {
        service.addCustomer(customer);
    }

    @Override
    public void deleteCustomer(Long ID) throws IllegalArgumentException {
        service.deleteCustomer(ID);
    }

    @Override
    public void updateCustomer(Customer customer) {
        service.updateCustomer(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @Override
    public List<Customer> getCustomersByAge(int age) {
        return service.getCustomersByAge(age);
    }

    @Override
    public void addBook(Book book) throws ValidatorException {
        service.addBook(book);
    }

    @Override
    public void deleteBook(Long ID) {
        service.deleteBook(ID);
    }

    @Override
    public void updateBook(Book book) {
        service.updateBook(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return service.getAllBooks();
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return service.getBooksByAuthor(author);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        service.addTransaction(transaction);
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        service.updateTransaction(transaction);
    }

    @Override
    public void deleteTransaction(Long ID) {
        service.deleteTransaction(ID);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return service.getAllTransactions();
    }

    @Override
    public Map<Book, Integer> getMostBoughtBooks(int noBooks) {
        return service.getMostBoughtBooks(noBooks);
    }
}
