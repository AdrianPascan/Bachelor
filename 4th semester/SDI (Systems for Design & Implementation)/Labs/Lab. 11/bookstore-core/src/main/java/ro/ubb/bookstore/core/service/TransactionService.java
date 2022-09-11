package ro.ubb.bookstore.core.service;

import ro.ubb.bookstore.core.model.Book;
import ro.ubb.bookstore.core.model.Pair;
import ro.ubb.bookstore.core.model.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    List<Transaction> getAllTransactions();
    Transaction saveTransaction(Transaction transaction);
    Transaction updateTransaction(Long id, Transaction transaction);
    void deleteTransactionById(Long id);
}
