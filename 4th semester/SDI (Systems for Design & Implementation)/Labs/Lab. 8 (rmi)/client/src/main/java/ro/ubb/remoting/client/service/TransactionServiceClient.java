package ro.ubb.remoting.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.remoting.common.TransactionService;
import ro.ubb.remoting.common.domain.Book;
import ro.ubb.remoting.common.domain.Transaction;

import java.util.List;
import java.util.Map;

public class TransactionServiceClient implements TransactionService {
    @Autowired
    private TransactionService transactionService;

    @Override
    public void addTransaction(Transaction transaction) {
        transactionService.addTransaction(transaction);
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        transactionService.updateTransaction(transaction);
    }

    @Override
    public void deleteTransaction(Long ID) {
        transactionService.deleteTransaction(ID);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @Override
    public Map<Book, Integer> getMostBoughtBooks(int noBooks) {
        return transactionService.getMostBoughtBooks(noBooks);
    }
}
