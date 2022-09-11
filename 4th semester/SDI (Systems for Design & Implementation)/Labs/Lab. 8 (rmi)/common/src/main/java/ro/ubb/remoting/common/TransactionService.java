package ro.ubb.remoting.common;

import ro.ubb.remoting.common.domain.Book;
import ro.ubb.remoting.common.domain.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    void addTransaction(Transaction transaction);
    void updateTransaction(Transaction transaction);
    void deleteTransaction(Long ID);
    List<Transaction> getAllTransactions();
    Map<Book, Integer> getMostBoughtBooks(int noBooks);
}
