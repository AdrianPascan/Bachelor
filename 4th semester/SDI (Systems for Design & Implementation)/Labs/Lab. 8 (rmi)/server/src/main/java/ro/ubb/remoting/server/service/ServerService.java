package ro.ubb.remoting.server.service;

import ro.ubb.remoting.common.Service;
import ro.ubb.remoting.common.domain.BaseEntity;
import ro.ubb.remoting.common.domain.Book;
import ro.ubb.remoting.common.domain.Customer;
import ro.ubb.remoting.common.domain.Transaction;
import ro.ubb.remoting.common.domain.validators.ValidatorException;
import ro.ubb.remoting.server.repository.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServerService implements Service {
    private Repository<Long, Customer> customerRepo;
    private Repository<Long, Book> bookRepo;
    private Repository<Long, Transaction> transactionRepo;

    public ServerService(Repository<Long, Customer> customerRepo, Repository<Long, Book> bookRepo, Repository<Long, Transaction> transactionRepo){
        this.customerRepo = customerRepo;
        this.bookRepo = bookRepo;
        this.transactionRepo = transactionRepo;
    }

    public void addCustomer(Customer customer) throws ValidatorException {
        customerRepo.save(customer);
    }

    public void deleteCustomer(Long ID) throws IllegalArgumentException {
        deleteTransactionsByCustomerID(ID);
        customerRepo.deleteById(ID);
    }

    public void updateCustomer(Customer customer){
        customerRepo.update(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public List<Customer> getCustomersByAge(int age) {
        return customerRepo.findAll().stream()
                .filter(customer -> age == customer.getAge())
                .collect(Collectors.toList());
    }

    public void addBook(Book book) throws ValidatorException {
        bookRepo.save(book);
    }

    public void deleteBook(Long ID) {
        deleteTransactionsByBookID(ID);
        bookRepo.deleteById(ID);
    }

    public void updateBook(Book book) {
        bookRepo.update(book);
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepo.findAll().stream()
                .filter(book -> author.equals(book.getAuthor()))
                .collect(Collectors.toList());
    }

    public void addTransaction(Transaction transaction) {
        transactionRepo.save(transaction);
    }

    public void updateTransaction(Transaction transaction) {
        transactionRepo.update(transaction);
    }

    public void deleteTransaction(Long ID) {
        transactionRepo.deleteById(ID);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    private void deleteTransactionsByBookID(Long bookID) {
        List<Long> transactionIDs = transactionRepo.findAll().stream()
                .filter(transaction -> transaction.getBookId() == bookID)
                .map(BaseEntity::getId).collect(Collectors.toList());

        transactionIDs.forEach(ID -> transactionRepo.deleteById(ID));
    }

    private void deleteTransactionsByCustomerID(Long customerID) {
        List<Long> transactionIDs = transactionRepo.findAll().stream()
                .filter(transaction -> transaction.getCustomerId() == customerID)
                .map(BaseEntity::getId).collect(Collectors.toList());

        transactionIDs.forEach(ID -> transactionRepo.deleteById(ID));
    }

    public Map<Book, Integer> getMostBoughtBooks(int noBooks) {
        Map<Long, List<Transaction>> groupedTransactions = transactionRepo.findAll().stream()
                .collect(Collectors.groupingBy(Transaction::getBookId));

        Map<Long, Integer> bookCount = groupedTransactions.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().size()));

        return bookCount.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .limit(noBooks).collect(Collectors.toMap( k -> bookRepo.findOne(k.getKey()), Map.Entry::getValue));
    }
}
