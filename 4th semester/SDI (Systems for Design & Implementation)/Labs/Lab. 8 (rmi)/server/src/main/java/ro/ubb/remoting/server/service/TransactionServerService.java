package ro.ubb.remoting.server.service;

import ro.ubb.remoting.common.TransactionService;
import ro.ubb.remoting.common.domain.BaseEntity;
import ro.ubb.remoting.common.domain.Book;
import ro.ubb.remoting.common.domain.Transaction;
import ro.ubb.remoting.server.repository.BookRepository;
import ro.ubb.remoting.server.repository.Repository;
import ro.ubb.remoting.server.repository.TransactionRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionServerService implements TransactionService {
    private Repository<Long, Transaction> transactionRepo;
    private Repository<Long, Book> bookRepo;

    public TransactionServerService(Repository<Long, Transaction> transactionRepo, Repository<Long, Book> bookRepo) {
        this.transactionRepo = transactionRepo;
        this.bookRepo = bookRepo;
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

    public Map<Book, Integer> getMostBoughtBooks(int noBooks) {
        Map<Long, List<Transaction>> groupedTransactions = transactionRepo.findAll().stream()
                .collect(Collectors.groupingBy(Transaction::getBookId));

        Map<Long, Integer> bookCount = groupedTransactions.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().size()));

        return bookCount.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .limit(noBooks).collect(Collectors.toMap( k -> bookRepo.findOne(k.getKey()), Map.Entry::getValue));
    }
}
