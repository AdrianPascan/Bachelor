package ro.ubb.bookstore.core.service;

import ro.ubb.bookstore.core.model.CustomerBook;

import java.util.List;

public interface TransactionService {
    CustomerBook getTransaction(Long customerId, Long bookId);
    List<CustomerBook> getAllTransactions();
    CustomerBook saveTransaction(CustomerBook customerBook);
    CustomerBook updateTransaction(CustomerBook customerBook);
    void deleteTransaction(Long customerId, Long bookId);
}
