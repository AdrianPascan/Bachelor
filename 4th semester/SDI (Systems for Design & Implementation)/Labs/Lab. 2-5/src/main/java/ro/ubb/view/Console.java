package ro.ubb.view;

import ro.ubb.controller.Controller;
import ro.ubb.domain.Book;
import ro.ubb.domain.Customer;
import ro.ubb.domain.Transaction;
import ro.ubb.domain.validators.BookStoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;

public class Console {
    private Controller controller;

    public Console(Controller controller){
        this.controller = controller;
    }

    public void start() {
        Map<String, Runnable> commands = getCommands();
        String menu = getMenu();
        String command = "";

        while (command != "0") {
            System.out.println(menu);
            command = readCommand();

            try {
                commands.get(command).run();
            }
            catch (NullPointerException ne){
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
        controller.addCustomer(customer);
    }

    private void deleteCustomerCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter customer ID: ");

        Long ID = scanner.nextLong();
        controller.deleteCustomer(ID);
    }

    private void updateCustomerCommand() {
        Long id = readId();
        Customer newCustomer = readCustomer();
        newCustomer.setId(id);
        controller.updateCustomer(newCustomer);
    }

    private void listCustomersCommand() {
        controller.getAllCustomers().forEach(customer -> System.out.println(customer.toString()));
    }

    private void filterCustomersByAgeCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter age:");
        int age = scanner.nextInt();

        controller.getCustomersByAge(age)
                .forEach(customer -> System.out.println(customer.toString()));
    }

    private void addBookCommand() {
        Book book = readBook();
        controller.addBook(book);
    }

    private void deleteBookCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter book ID: ");

        Long ID = scanner.nextLong();
        controller.deleteBook(ID);
    }

    private void updateBookCommand() {
        Long id = readId();
        Book newBook = readBook();
        newBook.setId(id);
        controller.updateBook(newBook);
    }

    private void listBooksCommand() {
        controller.getAllBooks().forEach(book -> System.out.println(book.toString()));
    }

    private void filterBooksByAuthorCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter author name:");
        String author = scanner.nextLine();

        controller.getBooksByAuthor(author)
                .forEach(book -> System.out.println(book.toString()));
    }

    private void addTransactionCommand() {
        Transaction transaction = readTransaction();
        controller.addTransaction(transaction);
    }

    private void deleteTransactionCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter transaction ID: ");

        Long ID = scanner.nextLong();
        controller.deleteTransaction(ID);
    }

    private void updateTransactionCommand() {
        Long id = readId();
        Transaction newTransaction = readTransaction();
        newTransaction.setId(id);
        controller.updateTransaction(newTransaction);
    }

    private void listTransactionsCommand() {
        controller.getAllTransactions().forEach(transaction -> System.out.println(transaction.toString()));
    }

    private void getTopBoughtBooks() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Amount of books: ");

        int n = scanner.nextInt();
        controller.getMostBoughtBooks(n).forEach((key, value) -> {
            System.out.println(key.toString() + ": " + value.toString());
        });
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
            throw new BookStoreException("BookStore Exception -> Invalid ISBN/year: ISBN/year is not a number");
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
        commands.put("26", this::getTopBoughtBooks);
        commands.put("31", this::addTransactionCommand);
        commands.put("32", this::deleteTransactionCommand);
        commands.put("33", this::updateTransactionCommand);
        commands.put("34", this::listTransactionsCommand);

        return commands;
    }

    private String getMenu() {
        StringBuilder menu = new StringBuilder("MENU\n");

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
        menu.append("26. get top bought books\n");
        menu.append("31. add transaction\n");
        menu.append("32. delete transaction\n");
        menu.append("33. update transaction\n");
        menu.append("34. list transactions\n");
        menu.append("0. EXIT\n");

        return menu.toString();
    }
}
