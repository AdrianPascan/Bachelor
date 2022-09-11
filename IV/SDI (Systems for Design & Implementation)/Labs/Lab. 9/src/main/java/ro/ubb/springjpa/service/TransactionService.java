package ro.ubb.springjpa.service;

import ro.ubb.springjpa.model.Book;
import ro.ubb.springjpa.model.Pair;
import ro.ubb.springjpa.model.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    List<Transaction> getAllTransactions();
    void saveTransaction(Transaction transaction);
    void updateTransaction(Transaction transaction);
    void deleteTransactionById(Long id);
    List<Pair<Long, Book>> getTopBoughtBooks(int noOfBooks);
}
