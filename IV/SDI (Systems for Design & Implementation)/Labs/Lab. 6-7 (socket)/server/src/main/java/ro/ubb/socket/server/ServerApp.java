package ro.ubb.socket.server;

import ro.ubb.socket.common.CommonService;
import ro.ubb.socket.common.Message;
import ro.ubb.socket.common.domain.Book;
import ro.ubb.socket.common.domain.Customer;
import ro.ubb.socket.common.domain.Transaction;
import ro.ubb.socket.common.domain.validators.BookValidator;
import ro.ubb.socket.common.domain.validators.CustomerValidator;
import ro.ubb.socket.common.domain.validators.TransactionValidator;
import ro.ubb.socket.common.domain.validators.Validator;
import ro.ubb.socket.server.repository.Repository;
import ro.ubb.socket.server.repository.database.BookDatabaseRepository;
import ro.ubb.socket.server.repository.database.CustomerDatabaseRepository;
import ro.ubb.socket.server.repository.database.TransactionDatabaseRepository;
import ro.ubb.socket.server.service.CommonServiceImpl;
import ro.ubb.socket.server.service.Controller;
import ro.ubb.socket.server.tcp.TcpServer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ServerApp {
    private static final String ERROR = "ERROR";
    private static final String OK = "ok";

    public static void main(String[] args) {
        Validator<Customer> customerValidator = new CustomerValidator();
        Validator<Book> bookValidator = new BookValidator();
        Validator<Transaction> transactionValidator = new TransactionValidator();
        Repository<Long, Customer> customerRepository = new CustomerDatabaseRepository(customerValidator);
        Repository<Long, Book> bookRepository = new BookDatabaseRepository(bookValidator);
        Repository<Long, Transaction> transactionRepository = new TransactionDatabaseRepository(transactionValidator);
        Controller controller = new Controller(customerRepository, bookRepository, transactionRepository);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CommonServiceImpl commonService = new CommonServiceImpl(controller, executorService);
        TcpServer tcpServer = new TcpServer(executorService);
        addHandlersToTcpServer(tcpServer, commonService);
        tcpServer.startServer();
        executorService.shutdown();
    }

    private static void addHandlersToTcpServer(TcpServer tcpServer, CommonServiceImpl commonService) {
        addBookHandlersToTcpServer(tcpServer, commonService);
        addCustomerHandlersToTcpServer(tcpServer, commonService);
        addTransactionHandlersToTcpServer(tcpServer, commonService);
    }

    private static void addBookHandlersToTcpServer(TcpServer tcpServer, CommonServiceImpl commonService) {
        tcpServer.addHandler(CommonService.ADD_BOOK, (request) -> {
            String input = request.getBody();
            Book book = getBookFromString(input, false);
            try {
                return new Message(OK, commonService.addBook(book).get());
            } catch (InterruptedException | ExecutionException e) {
                return new Message(ERROR, e.toString());
            }
        });

        tcpServer.addHandler(CommonService.DELETE_BOOK, (request) -> {
            String input = request.getBody();
            Long id = getIdFromString(input);
            try {
                return new Message(OK, commonService.deleteBook(id).get());
            } catch (InterruptedException | ExecutionException e) {
                return new Message(ERROR, e.toString());
            }
        });

        tcpServer.addHandler(CommonService.UPDATE_BOOK, (request) -> {
            String input = request.getBody();
            Book book = getBookFromString(input, true);
            try {
                return new Message(OK, commonService.updateBook(book).get());
            } catch (InterruptedException | ExecutionException e) {
                return new Message(ERROR, e.toString());
            }
        });

        tcpServer.addHandler(CommonService.LIST_BOOKS, (request) -> {
            try {
                return new Message(OK, commonService.listBooks().get());
            } catch (InterruptedException | ExecutionException e) {
                return new Message(ERROR, e.toString());
            }
        });

        tcpServer.addHandler(CommonService.LIST_BOOKS_BY_AUTHOR, (request) -> {
            String author = request.getBody();
            try {
                return new Message(OK, commonService.listBooksByAuthor(author).get());
            } catch (InterruptedException | ExecutionException e) {
                return new Message(ERROR, e.toString());
            }
        });
    }

    private static void addCustomerHandlersToTcpServer(TcpServer tcpServer, CommonServiceImpl commonService) {
        tcpServer.addHandler(CommonService.ADD_CUSTOMER, (request) -> {
            String input = request.getBody();
            Customer customer = getCustomerFromString(input, false);
            try {
                return new Message(OK, commonService.addCustomer(customer).get());
            } catch (InterruptedException | ExecutionException e) {
                return new Message(ERROR, e.toString());
            }
        });

        tcpServer.addHandler(CommonService.DELETE_CUSTOMER, (request) -> {
            String input = request.getBody();
            Long id = getIdFromString(input);
            try {
                return new Message(OK, commonService.deleteCustomer(id).get());
            } catch (InterruptedException | ExecutionException e) {
                return new Message(ERROR, e.toString());
            }
        });

        tcpServer.addHandler(CommonService.UPDATE_CUSTOMER, (request) -> {
            String input = request.getBody();
            Customer customer = getCustomerFromString(input, true);
            try {
                return new Message(OK, commonService.updateCustomer(customer).get());
            } catch (InterruptedException | ExecutionException e) {
                return new Message(ERROR, e.toString());
            }
        });

        tcpServer.addHandler(CommonService.LIST_CUSTOMER, (request) -> {
            try {
                return new Message(OK, commonService.listCustomers().get());
            } catch (InterruptedException | ExecutionException e) {
                return new Message(ERROR, e.toString());
            }
        });

        tcpServer.addHandler(CommonService.LIST_CUSTOMERS_BY_AGE, (request) -> {
            String input = request.getBody();
            int age = getIntFromString(input);
            try {
                return new Message(OK, commonService.listCustomersByAge(age).get());
            } catch (InterruptedException | ExecutionException e) {
                return new Message(ERROR, e.toString());
            }
        });
    }

    private static void addTransactionHandlersToTcpServer(TcpServer tcpServer, CommonServiceImpl commonService) {
        tcpServer.addHandler(CommonService.ADD_TRANSACTION, (request) -> {
            String input = request.getBody();
            Transaction transaction = getTransactionFromString(input, false);
            try {
                return new Message(OK, commonService.addTransaction(transaction).get());
            } catch (InterruptedException | ExecutionException e) {
                return new Message(ERROR, e.toString());
            }
        });

        tcpServer.addHandler(CommonService.DELETE_TRANSACTION, (request) -> {
            String input = request.getBody();
            Long id = getIdFromString(input);
            try {
                return new Message(OK, commonService.deleteTransaction(id).get());
            } catch (InterruptedException | ExecutionException e) {
                return new Message(ERROR, e.toString());
            }
        });

        tcpServer.addHandler(CommonService.UPDATE_TRANSACTION, (request) -> {
            String input = request.getBody();
            Transaction transaction = getTransactionFromString(input, true);
            try {
                return new Message(OK, commonService.updateTransaction(transaction).get());
            } catch (InterruptedException | ExecutionException e) {
                return new Message(ERROR, e.toString());
            }
        });

        tcpServer.addHandler(CommonService.LIST_TRANSACTION, (request) -> {
            try {
                return new Message(OK, commonService.listTransactions().get());
            } catch (InterruptedException | ExecutionException e) {
                return new Message(ERROR, e.toString());
            }
        });

        tcpServer.addHandler(CommonService.LIST_MOST_BOUGHT_BOOKS, (request) -> {
            String input = request.getBody();
            int noBooks = getIntFromString(input);
            try {
                return new Message(OK, commonService.listMostBoughtBooks(noBooks).get());
            } catch (InterruptedException | ExecutionException e) {
                return new Message(ERROR, e.toString());
            }
        });
    }

    private static Long getIdFromString(String input) {
        return Long.valueOf(input.split(",")[0]);
    }

    private static int getIntFromString(String input) {
        return Integer.parseInt(input.split(",")[0]);
    }

    private static Book getBookFromString(String input, boolean withId) {
        List<String> tokens = Arrays.asList(input.split(","));

        String isbn = tokens.get(0);
        String title = tokens.get(1);
        String author = tokens.get(2);
        String publishingHouse = tokens.get(3);
        int year = Integer.parseInt(tokens.get(4));
        float price = Float.parseFloat(tokens.get(5));

        Book book = new Book(isbn, title, author, publishingHouse, year, price);

        if (withId) {
            long id = Long.parseLong(tokens.get(6));
            book.setId(id);
        }

        return book;
    }

    private static Customer getCustomerFromString(String input, boolean withId) {
        List<String> tokens = Arrays.asList(input.split(","));

        String firstName = tokens.get(0);
        String lastName = tokens.get(1);
        int age = Integer.parseInt(tokens.get(2));

        Customer customer = new Customer(firstName, lastName, age);

        if (withId) {
            long id = Long.parseLong(tokens.get(3));
            customer.setId(id);
        }

        return customer;
    }

    private static Transaction getTransactionFromString(String input, boolean withId) {
        List<String> tokens = Arrays.asList(input.split(","));

        long customerId = Long.parseLong(tokens.get(0));
        long bookId = Long.parseLong(tokens.get(1));

        Transaction transaction = new Transaction(customerId, bookId);

        if (withId) {
            long id = Long.parseLong(tokens.get(2));
            transaction.setId(id);
        }

        return transaction;
    }
}
