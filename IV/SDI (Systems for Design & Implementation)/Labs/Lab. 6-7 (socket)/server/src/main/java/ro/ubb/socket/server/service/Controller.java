package ro.ubb.socket.server.service;

import ro.ubb.socket.common.domain.BaseEntity;
import ro.ubb.socket.common.domain.Book;
import ro.ubb.socket.common.domain.Customer;
import ro.ubb.socket.common.domain.Transaction;
import ro.ubb.socket.common.domain.validators.ValidatorException;
import ro.ubb.socket.server.repository.Repository;
import ro.ubb.socket.server.repository.database.BookDatabaseRepository;
import ro.ubb.socket.server.repository.database.CustomerDatabaseRepository;
import ro.ubb.socket.server.repository.database.TransactionDatabaseRepository;
import ro.ubb.socket.server.repository.sorting.Sort;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Controller {
    private Repository<Long, Customer> customerRepo;
    private Repository<Long, Book> bookRepo;
    private Repository<Long, Transaction> transactionRepo;

    public Controller(Repository<Long, Customer> customerRepo, Repository<Long, Book> bookRepo, Repository<Long, Transaction> transactionRepo){
        this.customerRepo = customerRepo;
        this.bookRepo = bookRepo;
        this.transactionRepo = transactionRepo;
    }

    public Optional<Customer> addCustomer(Customer customer) throws ValidatorException {
        return customerRepo.save(customer);
    }

    public Optional<Customer> deleteCustomer(Long ID) throws IllegalArgumentException {
        deleteTransactionsByCustomerID(ID);
        return customerRepo.delete(ID);
    }

    public Optional<Customer> updateCustomer(Customer customer){
        return customerRepo.update(customer);
    }

    public List<Customer> getAllCustomers() {
        Iterable<Customer> customers = customerRepo instanceof CustomerDatabaseRepository ?
                ((CustomerDatabaseRepository) customerRepo).findAll(new Sort("age")) :
                customerRepo.findAll();
        return StreamSupport.stream(customers.spliterator(), false).collect(Collectors.toList());
    }

    public List<Customer> getCustomersByAge(int age) {
        Iterable<Customer> iterable = customerRepo.findAll();
        List<Customer> customers = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
        return customers.stream()
                .filter(customer -> age == customer.getAge())
                .collect(Collectors.toList());
    }

    public Optional<Book> addBook(Book book) throws ValidatorException {
        return bookRepo.save(book);
    }

    public Optional<Book> deleteBook(Long ID) {
        deleteTransactionsByBookID(ID);
        return bookRepo.delete(ID);
    }

    public Optional<Book> updateBook(Book book) {
        return bookRepo.update(book);
    }

    public List<Book> getAllBooks() {
        Iterable<Book> books = bookRepo instanceof BookDatabaseRepository ?
                ((BookDatabaseRepository) bookRepo).findAll(new Sort("title")) :
                bookRepo.findAll();
        return StreamSupport.stream(books.spliterator(), false).collect(Collectors.toList());
    }

    public List<Book> getBooksByAuthor(String author) {
        Iterable<Book> iterable = bookRepo.findAll();
        List<Book> customers = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
        return customers.stream()
                .filter(book -> author.equals(book.getAuthor()))
                .collect(Collectors.toList());
    }

    public Optional<Transaction> addTransaction(Transaction transaction) {
        return transactionRepo.save(transaction);
    }

    public Optional<Transaction> updateTransaction(Transaction transaction) {
        return transactionRepo.update(transaction);
    }

    public Optional<Transaction> deleteTransaction(Long ID) {
        return transactionRepo.delete(ID);
    }

    public List<Transaction> getAllTransactions() {
        Iterable<Transaction> transactions = transactionRepo instanceof TransactionDatabaseRepository ?
                ((TransactionDatabaseRepository) transactionRepo).findAll(new Sort("customerId")) :
                transactionRepo.findAll();
        return StreamSupport.stream(transactions.spliterator(), false).collect(Collectors.toList());
    }

    private void deleteTransactionsByBookID(Long bookID) {
        List<Long> transactionIDs = StreamSupport.stream(transactionRepo.findAll().spliterator(), false)
                .filter(transaction -> transaction.getBookId() == bookID)
                .map(BaseEntity::getId).collect(Collectors.toList());

        transactionIDs.forEach(ID -> transactionRepo.delete(ID));
    }

    private void deleteTransactionsByCustomerID(Long customerID) {
        List<Long> transactionIDs = StreamSupport.stream(transactionRepo.findAll().spliterator(), false)
                .filter(transaction -> transaction.getCustomerId() == customerID)
                .map(BaseEntity::getId).collect(Collectors.toList());

        transactionIDs.forEach(ID -> transactionRepo.delete(ID));
    }

    public Map<Book, Integer> getMostBoughtBooks(int noBooks) {
        Map<Long, List<Transaction>> groupedTransactions = StreamSupport.stream(transactionRepo.findAll().spliterator(), false)
                .collect(Collectors.groupingBy(Transaction::getBookId));

        Map<Long, Integer> bookCount = groupedTransactions.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().size()));

        return bookCount.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .limit(noBooks).collect(Collectors.toMap( k -> bookRepo.findOne(k.getKey()).get(), Map.Entry::getValue));
    }
}
