package ro.ubb.remoting.common;

import ro.ubb.remoting.common.domain.*;
import ro.ubb.remoting.common.domain.validators.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface Service {
    public void addCustomer(Customer customer) throws ValidatorException;
    public void deleteCustomer(Long ID) throws IllegalArgumentException;
    public void updateCustomer(Customer customer);
    public List<Customer> getAllCustomers();
    public List<Customer> getCustomersByAge(int age);

    public void addBook(Book book) throws ValidatorException;
    public void deleteBook(Long ID);
    public void updateBook(Book book);
    public List<Book> getAllBooks();
    public List<Book> getBooksByAuthor(String author);

    public void addTransaction(Transaction transaction);
    public void updateTransaction(Transaction transaction);
    public void deleteTransaction(Long ID);
    public List<Transaction> getAllTransactions();
    public Map<Book, Integer> getMostBoughtBooks(int noBooks);
}
