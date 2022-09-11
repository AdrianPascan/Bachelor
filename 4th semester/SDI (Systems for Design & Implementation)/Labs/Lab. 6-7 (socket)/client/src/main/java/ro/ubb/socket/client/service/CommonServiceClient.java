package ro.ubb.socket.client.service;

import ro.ubb.socket.client.tcp.TcpClient;
import ro.ubb.socket.common.CommonService;
import ro.ubb.socket.common.Message;
import ro.ubb.socket.common.domain.Book;
import ro.ubb.socket.common.domain.Customer;
import ro.ubb.socket.common.domain.Transaction;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


public class CommonServiceClient implements CommonService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public CommonServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public CompletableFuture<String> addBook(Book book) {
        return CompletableFuture.supplyAsync((() -> {
            String body = book.getIsbn()+","+book.getTitle()+","+book.getAuthor()+","+book.getPublishingHouse()+","+Integer.toString(book.getYear())+","+Float.toString(book.getPrice());
            Message request = new Message(CommonService.ADD_BOOK, body);
            Message response = tcpClient.sendAndReceive(request);
            return (String)response.getBody();
        }),executorService);
    }

    @Override
    public CompletableFuture<String> deleteBook(Long id) {
        return CompletableFuture.supplyAsync((() -> {
            Message request = new Message(CommonService.DELETE_BOOK, id.toString());
            Message response = tcpClient.sendAndReceive(request);
            return (String)response.getBody();
        }),executorService);
    }

    @Override
    public CompletableFuture<String> updateBook(Book book) {
        return CompletableFuture.supplyAsync((() -> {
            String body = book.getIsbn()+","+book.getTitle()+","+book.getAuthor()+","+book.getPublishingHouse()+","+Integer.toString(book.getYear())+","+Float.toString(book.getPrice())+","+Long.toString(book.getId());
            Message request = new Message(CommonService.UPDATE_BOOK, body);
            Message response = tcpClient.sendAndReceive(request);
            return (String)response.getBody();
        }),executorService);
    }

    @Override
    public CompletableFuture<String> listBooks() {
        return CompletableFuture.supplyAsync((() -> {
            Message request = new Message(CommonService.LIST_BOOKS, "");
            Message response = tcpClient.sendAndReceive(request);
            return (String)response.getBody();
        }),executorService);
    }

    @Override
    public CompletableFuture<String> listBooksByAuthor(String author) {
        return CompletableFuture.supplyAsync((() -> {
            Message request = new Message(CommonService.LIST_BOOKS_BY_AUTHOR, author);
            Message response = tcpClient.sendAndReceive(request);
            return (String)response.getBody();
        }),executorService);
    }

    @Override
    public CompletableFuture<String> addCustomer(Customer customer) {
        return CompletableFuture.supplyAsync((() -> {
            String body = customer.getFirstName() + "," + customer.getLastName() + "," + Integer.toString(customer.getAge());
            Message request = new Message(CommonService.ADD_CUSTOMER, body);
            Message response = tcpClient.sendAndReceive(request);
            return (String)response.getBody();
        }),executorService);
    }

    @Override
    public CompletableFuture<String> deleteCustomer(Long id) {
        return CompletableFuture.supplyAsync((() -> {
            Message request = new Message(CommonService.DELETE_CUSTOMER, id.toString());
            Message response = tcpClient.sendAndReceive(request);
            return (String)response.getBody();
        }),executorService);
    }

    @Override
    public CompletableFuture<String> updateCustomer(Customer customer) {
        return CompletableFuture.supplyAsync((() -> {
            String body = customer.getFirstName() + "," + customer.getLastName() + "," + Integer.toString(customer.getAge()) + "," + Long.toString(customer.getId());
            Message request = new Message(CommonService.UPDATE_CUSTOMER, body);
            Message response = tcpClient.sendAndReceive(request);
            return (String)response.getBody();
        }),executorService);
    }

    @Override
    public CompletableFuture<String> listCustomers() {
        return CompletableFuture.supplyAsync((() -> {
            Message request = new Message(CommonService.LIST_CUSTOMER, "");
            Message response = tcpClient.sendAndReceive(request);
            return response.getBody();
        }),executorService);
    }

    @Override
    public CompletableFuture<String> listCustomersByAge(int age) {
        return CompletableFuture.supplyAsync((() -> {
            Message request = new Message(CommonService.LIST_CUSTOMERS_BY_AGE, Integer.toString(age));
            Message response = tcpClient.sendAndReceive(request);
            return (String)response.getBody();
        }),executorService);
    }

    @Override
    public CompletableFuture<String> addTransaction(Transaction transaction) {
        return CompletableFuture.supplyAsync((() -> {
            String body = Long.toString(transaction.getCustomerId()) + "," + Long.toString(transaction.getBookId());
            Message request = new Message(CommonService.ADD_TRANSACTION, body);
            Message response = tcpClient.sendAndReceive(request);
            return (String)response.getBody();
        }),executorService);
    }

    @Override
    public CompletableFuture<String> deleteTransaction(Long id) {
        return CompletableFuture.supplyAsync((() -> {
            Message request = new Message(CommonService.DELETE_TRANSACTION, id.toString());
            Message response = tcpClient.sendAndReceive(request);
            return (String)response.getBody();
        }),executorService);
    }

    @Override
    public CompletableFuture<String> updateTransaction(Transaction transaction) {
        return CompletableFuture.supplyAsync((() -> {
            String body = Long.toString(transaction.getCustomerId()) + "," + Long.toString(transaction.getBookId()) + "," + Long.toString(transaction.getId());
            Message request = new Message(CommonService.UPDATE_TRANSACTION, body);
            Message response = tcpClient.sendAndReceive(request);
            return (String)response.getBody();
        }),executorService);
    }

    @Override
    public CompletableFuture<String> listTransactions() {
        return CompletableFuture.supplyAsync((() -> {
            Message request = new Message(CommonService.LIST_TRANSACTION, "");
            Message response = tcpClient.sendAndReceive(request);
            return (String)response.getBody();
        }),executorService);
    }

    @Override
    public CompletableFuture<?> listMostBoughtBooks(int noBooks) {
        return CompletableFuture.supplyAsync((() -> {
            Message request = new Message(CommonService.LIST_MOST_BOUGHT_BOOKS, Integer.toString(noBooks));
            Message response = tcpClient.sendAndReceive(request);
            return (String)response.getBody();
        }),executorService);
    }
}
