package ro.ubb.bookstore.client.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import ro.ubb.bookstore.client.controller.BookController;
import ro.ubb.bookstore.client.controller.CustomerController;
import ro.ubb.bookstore.client.controller.TransactionController;
import ro.ubb.bookstore.core.model.Book;
import ro.ubb.bookstore.core.model.Customer;
import ro.ubb.bookstore.core.model.Pair;
import ro.ubb.bookstore.core.model.Transaction;
import ro.ubb.bookstore.core.model.validators.BookStoreException;
import ro.ubb.bookstore.web.dto.*;

import java.util.*;

import static java.lang.System.exit;

@Component
public class Console {
    @Autowired
    private CustomerController customerController;

    @Autowired
    private BookController bookController;

    @Autowired
    private TransactionController transactionController;


    public void start() {
        Map<String, Runnable> commands = getCommands();
        String menu = getMenu();
        String command = "";

        while (!command.equals("0")) {
            System.out.println(menu);
            command = readCommand();

            try {
                commands.get(command).run();
            }
            catch (NullPointerException npe){
                System.out.println("Invalid command..");
            }
            catch (RuntimeException re){
                System.out.println(re.getMessage());
            }
        }
    }

    private void exitCommand(){
        System.out.println("Closing application...");
        System.out.println("Application has been closed.");
        exit(0);
    }

    private void addCustomerCommand() {
        Customer customer = readCustomer();

        customerController.addCustomer(customer);

        System.out.println("Customer saved.");
    }

    private void deleteCustomerCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter customer ID: ");
        Long id = scanner.nextLong();

        customerController.deleteCustomer(id);

        System.out.println("Customer with id=" + Long.toString(id) + " deleted.");
    }

    private void updateCustomerCommand() {
        Long id = readId();
        Customer newCustomer = readCustomer();
        newCustomer.setId(id);

        customerController.updateCustomer(newCustomer);

        System.out.println("Customer with id=" + Long.toString(id) + " updated.");
    }

    private void listCustomersCommand() {
        Set<Customer> customers = customerController.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("There are no customers stored..");
        } else {
            customers.forEach(System.out::println);
        }
    }

    private void filterCustomersByAgeCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter age:");
        int age = scanner.nextInt();

        Set<Customer> customers = customerController.getCustomersByAge(age);

        if (customers.isEmpty()) {
            System.out.println("There are no customers having age=" + Integer.toString(age) + " ..");
        } else {
            customers.forEach(System.out::println);
        }
    }

    private void addBookCommand() {
        Book book = readBook();

        bookController.addBook(book);

        System.out.println("Book saved.");
    }

    private void deleteBookCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter book ID: ");
        Long id = scanner.nextLong();

        bookController.deleteBook(id);

        System.out.println("Book with id=" + Long.toString(id) + " deleted.");
    }

    private void updateBookCommand() {
        Long id = readId();
        Book newBook = readBook();
        newBook.setId(id);

        bookController.updateBook(newBook);

        System.out.println("Book with id=" + Long.toString(id) + " updated.");
    }

    private void listBooksCommand() {
        Set<Book> books = bookController.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("There are no books stored..");
        } else {
            books.forEach(System.out::println);
        }
    }

    private void filterBooksByAuthorCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter author name:");
        String author = scanner.nextLine();

        Set<Book> books = bookController.getBooksByAuthor(author);

        if (books.isEmpty()) {
            System.out.println("There are no books having author='"+ author + "' ..");
        } else {
            books.forEach(System.out::println);
        }
    }

    private void addTransactionCommand() {
        Transaction transaction = readTransaction();

        transactionController.addTransaction(transaction);

        System.out.println("Transaction saved.");
    }

    private void deleteTransactionCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter transaction ID: ");
        Long id = scanner.nextLong();

        transactionController.deleteTransaction(id);

        System.out.println("Transaction with id=" + Long.toString(id) + " deleted.");
    }

    private void updateTransactionCommand() {
        Long id = readId();
        Transaction newTransaction = readTransaction();
        newTransaction.setId(id);

        transactionController.updateTransaction(newTransaction);

        System.out.println("Transaction with id=" + Long.toString(id) + " updated.");
    }

    private void listTransactionsCommand() {
        Set<Transaction> transactions = transactionController.getAllTransactions();

        if (transactions.isEmpty()) {
            System.out.println("There are no transactions stored..");
        } else {
            transactions.forEach(System.out::println);
        }
    }

    private void getTopBoughtBooks() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("No. of books: ");
        int noOfBooks = scanner.nextInt();
        if (noOfBooks <= 0) {
            throw new BookStoreException("BookStore Exception -> No. of books must be greater than 0.");
        }

        List<Pair<Long, Book>> topBoughtBooks = transactionController.getTopBoughtBooks(noOfBooks);

        if (topBoughtBooks.isEmpty()) {
            System.out.println("There are no transactions stored..");
        } else {
            topBoughtBooks.forEach(pair -> System.out.println(Long.toString(pair.getFirst()) + ": " + pair.getSecond()));
        }
    }

    private String readCommand() {
        System.out.println("\t >>> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private Long readId() {
        System.out.println("ID: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLong();
    }

    private Customer readCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter customer details:");

        System.out.println("\t>> first name: ");
        String firstName = scanner.nextLine();

        System.out.println("\t>> last name: ");
        String lastName = scanner.nextLine();

        System.out.println("\t>> age: ");
        try {
            int age = scanner.nextInt();
            Customer customer = new Customer(firstName, lastName, age);
            return customer;
        }
        catch (RuntimeException e){
            throw new BookStoreException("BookStore Exception -> Invalid Age: age is not a number");
        }
    }

    private Book readBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter book details:");

        try {
            System.out.println("\t>> ISBN: ");
            String isbn = scanner.nextLine();

            System.out.println("\t>> title: ");
            String title = scanner.nextLine();

            System.out.println("\t>> author: ");
            String author = scanner.nextLine();

            System.out.println("\t>> publishing house: ");
            String publishingHouse = scanner.nextLine();

            System.out.println("\t>> year: ");
            int year = scanner.nextInt();

            System.out.println("\t>> price: ");
            float price = scanner.nextFloat();

            Book book = new Book(isbn, title, author, publishingHouse, year, price);
            return book;
        }
        catch (RuntimeException e){
            throw new BookStoreException("BookStore Exception -> Invalid year/price: year/price is not a number");
        }
    }

    private Transaction readTransaction() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter transaction details:");

        try {
            System.out.println("\t>> customer ID: ");
            Long customerId = scanner.nextLong();

            System.out.println("\t>> book ID: ");
            Long bookId = scanner.nextLong();

            Transaction transaction = new Transaction(customerId, bookId);
            return transaction;
        }
        catch (RuntimeException e){
            throw new BookStoreException("BookStore Exception -> Invalid ID: ID is not a number");
        }
    }

    private Map<String, Runnable> getCommands() {
        Map<String, Runnable> commands = new HashMap<>();

        commands.put("0", this::exitCommand);
        commands.put("11", this::addCustomerCommand);
        commands.put("12", this::deleteCustomerCommand);
        commands.put("13", this::updateCustomerCommand);
        commands.put("14", this::listCustomersCommand);
        commands.put("15", this::filterCustomersByAgeCommand);
        commands.put("21", this::addBookCommand);
        commands.put("22", this::deleteBookCommand);
        commands.put("23", this::updateBookCommand);
        commands.put("24", this::listBooksCommand);
        commands.put("25", this::filterBooksByAuthorCommand);
        commands.put("31", this::addTransactionCommand);
        commands.put("32", this::deleteTransactionCommand);
        commands.put("33", this::updateTransactionCommand);
        commands.put("34", this::listTransactionsCommand);
        commands.put("35", this::getTopBoughtBooks);

        return commands;
    }

    private String getMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("_______________________________\n");
        menu.append("MENU\n");

        menu.append("11. add customer\n");
        menu.append("12. delete customer\n");
        menu.append("13. update customer\n");
        menu.append("14. list customers\n");
        menu.append("15. filter customers by age\n");
        menu.append("21. add book\n");
        menu.append("22. delete book\n");
        menu.append("23. update book\n");
        menu.append("24. list books\n");
        menu.append("25. filter books by author\n");
        menu.append("31. add transaction\n");
        menu.append("32. delete transaction\n");
        menu.append("33. update transaction\n");
        menu.append("34. list transactions\n");
        menu.append("35. get top bought books\n");
        menu.append("0. EXIT\n");

        return menu.toString();
    }
}
