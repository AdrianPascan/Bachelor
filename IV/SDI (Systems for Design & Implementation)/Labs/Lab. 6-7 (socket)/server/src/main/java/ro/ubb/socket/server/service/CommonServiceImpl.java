package ro.ubb.socket.server.service;

import ro.ubb.socket.common.CommonService;
import ro.ubb.socket.common.domain.Book;
import ro.ubb.socket.common.domain.Customer;
import ro.ubb.socket.common.domain.Transaction;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CommonServiceImpl implements CommonService {
    private Controller controller;
    private ExecutorService executorService;

    public CommonServiceImpl(Controller controller, ExecutorService executorService) {
        this.controller = controller;
        this.executorService = executorService;
    }

    @Override
    public CompletableFuture<String> addBook(Book book) {
        return CompletableFuture.supplyAsync(() -> {
                    controller.addBook(book);
                    return "Executed add book: " + book.toString();
                },
                executorService);
    }

    @Override
    public CompletableFuture<String> deleteBook(Long id) {
        return CompletableFuture.supplyAsync(() -> {
                    controller.deleteBook(id);
                    return "Executed delete book: id= " + Long.toString(id);
                },
                executorService);
    }

    @Override
    public CompletableFuture<String> updateBook(Book book) {
        return CompletableFuture.supplyAsync(() -> {
                    controller.updateBook(book);
                    return "Executed update book: " + book.toString();
                },
                executorService);
    }

    @Override
    public CompletableFuture<String> listBooks() {
        return CompletableFuture.supplyAsync(() -> {
                    return controller.getAllBooks().stream()
                            .map(book -> book.toString())
                            .reduce("", (accumulator, current) -> accumulator += current + " | ");
                },
                executorService);
    }

    @Override
    public CompletableFuture<String> listBooksByAuthor(String author) {
        return CompletableFuture.supplyAsync(() -> {
                    return controller.getBooksByAuthor(author).stream()
                            .map(book -> book.toString())
                            .reduce("", (accumulator, current) -> accumulator += current + " | ");
                },
                executorService);
    }

    @Override
    public CompletableFuture<String> addCustomer(Customer customer) {
        return CompletableFuture.supplyAsync(() -> {
                    controller.addCustomer(customer);
                    return "Executed add customer: " + customer.toString();
                },
                executorService);
    }

    @Override
    public CompletableFuture<String> deleteCustomer(Long id) {
        return CompletableFuture.supplyAsync(() -> {
                    controller.deleteCustomer(id);
                    return "Executed delete customer: id= " + Long.toString(id);
                },
                executorService);
    }

    @Override
    public CompletableFuture<String> updateCustomer(Customer customer) {
        return CompletableFuture.supplyAsync(() -> {
                    controller.updateCustomer(customer);
                    return "Executed update customer: " + customer.toString();
                },
                executorService);
    }

    @Override
    public CompletableFuture<String> listCustomers() {
        return CompletableFuture.supplyAsync(() -> {
                    return controller.getAllCustomers().stream()
                            .map(customer -> customer.toString())
                            .reduce("", (accumulator, current) -> accumulator += current + " | ");
                },
                executorService);
    }

    @Override
    public CompletableFuture<String> listCustomersByAge(int age) {
        return CompletableFuture.supplyAsync(() -> {
                    return controller.getCustomersByAge(age).stream()
                            .map(customer -> customer.toString())
                            .reduce("", (accumulator, current) -> accumulator += current + " | ");
                },
                executorService);
    }

    @Override
    public CompletableFuture<String> addTransaction(Transaction transaction) {
        return CompletableFuture.supplyAsync(() -> {
                    controller.addTransaction(transaction);
                    return "Executed add transaction: " + transaction.toString();
                },
                executorService);
    }

    @Override
    public CompletableFuture<String> deleteTransaction(Long id) {
        return CompletableFuture.supplyAsync(() -> {
                    controller.deleteTransaction(id);
                    return "Executed delete transaction: id= " + Long.toString(id);
                },
                executorService);
    }

    @Override
    public CompletableFuture<String> updateTransaction(Transaction transaction) {
        return CompletableFuture.supplyAsync(() -> {
                    controller.updateTransaction(transaction);
                    return "Executed update transaction: " + transaction.toString();
                },
                executorService);
    }

    @Override
    public CompletableFuture<String> listTransactions() {
        return CompletableFuture.supplyAsync(() -> {
                    return controller.getAllTransactions().stream()
                            .map(transaction -> transaction.toString())
                            .reduce("", (accumulator, current) -> accumulator += current + " | ");
                },
                executorService);
    }

    @Override
    public CompletableFuture<String> listMostBoughtBooks(int noBooks) {
        return CompletableFuture.supplyAsync(() -> {
                    return controller.getMostBoughtBooks(noBooks)
                            .entrySet().stream()
                            .map(bookIntegerEntry -> bookIntegerEntry.getValue() + ": " + bookIntegerEntry.getKey())
                            .reduce("", (accumulator, current) -> accumulator += current + " | ");
                },
                executorService);
    }
}
