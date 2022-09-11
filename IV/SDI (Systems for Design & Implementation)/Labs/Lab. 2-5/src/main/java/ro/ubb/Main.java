package ro.ubb;

import ro.ubb.controller.Controller;
import ro.ubb.domain.Book;
import ro.ubb.domain.Customer;
import ro.ubb.domain.Transaction;
import ro.ubb.domain.validators.BookValidator;
import ro.ubb.domain.validators.CustomerValidator;
import ro.ubb.domain.validators.TransactionValidator;
import ro.ubb.domain.validators.Validator;
import ro.ubb.repository.InMemoryRepository;
import ro.ubb.repository.InMemoryRepositoryLongId;
import ro.ubb.repository.Repository;
import ro.ubb.repository.database.BookDatabaseRepository;
import ro.ubb.repository.database.CustomerDatabaseRepository;
import ro.ubb.repository.database.TransactionDatabaseRepository;
import ro.ubb.repository.file.BookFileRepository;
import ro.ubb.repository.file.CustomerFileRepository;
import ro.ubb.repository.file.TransactionFileRepository;
import ro.ubb.repository.xml.BookXMLRepository;
import ro.ubb.repository.xml.CustomerXMLRepository;
import ro.ubb.repository.xml.TransactionXMLRepository;
import ro.ubb.view.Console;

public class Main {
    public static void main(String[] args) {
        Validator<Customer> customerValidator = new CustomerValidator();
        Validator<Book> bookValidator = new BookValidator();
        Validator<Transaction> transactionValidator = new TransactionValidator();
//        Repository<Long, Customer> customerRepository = new InMemoryRepositoryLongId<>(customerValidator);
        Repository<Long, Customer> customerRepository = new CustomerFileRepository(customerValidator, "./data/customers.csv");
//        Repository<Long, Customer> customerRepository = new CustomerXMLRepository(customerValidator, "./data/customers.xml");
//        Repository<Long, Customer> customerRepository = new CustomerDatabaseRepository(customerValidator);
//        Repository<Long, Book> bookRepository = new InMemoryRepositoryLongId<>(bookValidator);
//        Repository<Long, Book> bookRepository = new BookFileRepository(bookValidator, "./data/books.csv");
        Repository<Long, Book> bookRepository = new BookXMLRepository(bookValidator, "./data/books.xml");
//        Repository<Long, Book> bookRepository = new BookDatabaseRepository(bookValidator);
//        Repository<Long, Transaction> transactionRepository = new InMemoryRepositoryLongId<>(transactionValidator);
//        Repository<Long, Transaction> transactionRepository = new TransactionFileRepository(transactionValidator, "./data/transactions.csv");
//        Repository<Long, Transaction> transactionRepository = new TransactionXMLRepository(transactionValidator, "./data/transactions.xml");
        Repository<Long, Transaction> transactionRepository = new TransactionDatabaseRepository(transactionValidator);
        Controller controller = new Controller(customerRepository, bookRepository, transactionRepository);
        Console console = new Console(controller);

        console.start();
    }
}
