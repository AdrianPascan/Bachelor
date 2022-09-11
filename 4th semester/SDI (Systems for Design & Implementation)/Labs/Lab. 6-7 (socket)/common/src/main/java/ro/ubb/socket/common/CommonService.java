package ro.ubb.socket.common;

import ro.ubb.socket.common.domain.Book;
import ro.ubb.socket.common.domain.Customer;
import ro.ubb.socket.common.domain.Transaction;

import java.util.concurrent.CompletableFuture;

public interface CommonService {
    String ADD_BOOK = "addBook";
    String DELETE_BOOK = "deleteBook";
    String UPDATE_BOOK = "updateBook";
    String LIST_BOOKS = "getAllBooks";
    String LIST_BOOKS_BY_AUTHOR = "getBooksByAuthor";

    String ADD_CUSTOMER = "addCustomer";
    String DELETE_CUSTOMER = "deleteCustomer";
    String UPDATE_CUSTOMER = "updateCustomer";
    String LIST_CUSTOMER = "getAllCustomers";
    String LIST_CUSTOMERS_BY_AGE = "getCustomersByAge";

    String ADD_TRANSACTION = "addTransaction";
    String DELETE_TRANSACTION = "deleteTransaction";
    String UPDATE_TRANSACTION = "updateTransaction";
    String LIST_TRANSACTION = "getAllTransactions";
    String LIST_MOST_BOUGHT_BOOKS = "getMostBoughtBooks";

    CompletableFuture<String> addBook(Book book);
    CompletableFuture<String> deleteBook(Long id);
    CompletableFuture<String> updateBook(Book book);
    CompletableFuture<String> listBooks();
    CompletableFuture<String> listBooksByAuthor(String author);

    CompletableFuture<String> addCustomer(Customer customer);
    CompletableFuture<String> deleteCustomer(Long id);
    CompletableFuture<String> updateCustomer(Customer customer);
    CompletableFuture<String> listCustomers();
    CompletableFuture<String> listCustomersByAge(int age);

    CompletableFuture<String> addTransaction(Transaction transaction);
    CompletableFuture<String> deleteTransaction(Long id);
    CompletableFuture<String> updateTransaction(Transaction transaction);
    CompletableFuture<String> listTransactions();
    CompletableFuture<?> listMostBoughtBooks(int noBooks);
}
