package ro.ubb.bookstore.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ro.ubb.bookstore.core.model.Book;
import ro.ubb.bookstore.core.model.Customer;
import ro.ubb.bookstore.core.model.Pair;
import ro.ubb.bookstore.core.model.Transaction;
import ro.ubb.bookstore.core.model.validators.BookStoreException;
import ro.ubb.bookstore.core.model.validators.CustomerValidator;
import ro.ubb.bookstore.core.model.validators.TransactionValidator;
import ro.ubb.bookstore.web.converter.BookConverter;
import ro.ubb.bookstore.web.converter.CustomerConverter;
import ro.ubb.bookstore.web.converter.TransactionConverter;
import ro.ubb.bookstore.web.dto.CustomerDto;
import ro.ubb.bookstore.web.dto.CustomersDto;
import ro.ubb.bookstore.web.dto.TransactionsDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class TransactionController {
    private static final String URL = "http://localhost:8081/api";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionValidator transactionValidator;

    @Autowired
    private TransactionConverter transactionConverter;

    @Autowired
    private BookController bookController;


    public void addTransaction(Transaction transaction) {
        transactionValidator.validate(transaction);

        try {
            restTemplate.postForObject(
                    URL + "/transactions",
                    transactionConverter.convertModelToDto(transaction),
                    TransactionsDto.class
            );
        }
        catch (HttpServerErrorException exception) {
            throw new BookStoreException("BookStore Exception -> Customer with id=" + Long.toString(transaction.getCustomerId()) + " or/and book with id=" + Long.toString(transaction.getBookId()) + " does/do not exist.");
        }
    }

    public void deleteTransaction(Long id) {
        try {
            restTemplate.delete(
                    URL + "/transactions/{id}",
                    id
            );
        }
        catch (HttpServerErrorException exception) {
            throw new BookStoreException("BookStore Exception -> Transaction with id=" + Long.toString(id) + " does not exist.");
        }
    }

    public void updateTransaction(Transaction transaction) {
        transactionValidator.validate(transaction);

        try{
            restTemplate.put(
                    URL + "/transactions/{id}",
                    transactionConverter.convertModelToDto(transaction),
                    transaction.getId()
            );
        }
        catch (HttpServerErrorException exception) {
            throw new BookStoreException("BookStore Exception -> Transaction with id=" + Long.toString(transaction.getId()) + " does not exist | Customer with id=" + Long.toString(transaction.getCustomerId()) + " or/and book with id=" + Long.toString(transaction.getBookId()) + " does/do not exist.");
        }
    }

    public Set<Transaction> getAllTransactions() {
        TransactionsDto dtos = restTemplate.getForObject(
                URL + "/transactions",
                TransactionsDto.class
        );
        return transactionConverter.convertDtosToModels(dtos.getTransactions());
    }

    public List<Pair<Long, Book>> getTopBoughtBooks(int noOfBooks) {
        Set<Book> books = bookController.getAllBooks();
        Set<Transaction> transactions = this.getAllTransactions();

        List<Pair<Long, Book>> result =
                books.stream()
                        .map(book -> (Pair<Long, Book>) new Pair<Long, Book>(
                                transactions.stream().filter(transaction -> transaction.getBookId().equals(book.getId())).count(),
                                book
                        ))
                        .sorted((pair, pair2) -> -Long.compare(pair.getFirst(), pair2.getFirst()))
                        .limit(noOfBooks)
                        .collect(Collectors.toList());

        return result;
    }
}
