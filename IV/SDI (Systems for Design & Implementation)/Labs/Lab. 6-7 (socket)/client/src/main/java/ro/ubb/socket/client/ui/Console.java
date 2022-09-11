package ro.ubb.socket.client.ui;

import ro.ubb.socket.common.CommonService;
import ro.ubb.socket.common.CommonServiceException;
import ro.ubb.socket.common.domain.Book;
import ro.ubb.socket.common.domain.Customer;
import ro.ubb.socket.common.domain.Transaction;
import ro.ubb.socket.common.domain.validators.BookStoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.System.exit;

/**
 * Created by radu.
 */
public class Console {
    private CommonService commonService;

    public Console(CommonService commonService) {
        this.commonService = commonService;
    }

    public void runConsole() {
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
        System.out.println("Application closed.");
        exit(0);
    }

    private void addCustomerCommand() {
        Customer customer = readCustomer();
        try{
            commonService.addCustomer(customer).thenAccept(result -> {
                        if (result.contains("ERROR")) System.err.println(result);
                        else System.out.println(result);
                    });
        }
        catch (CommonServiceException e){
            System.out.println(e.getMessage());
        }

    }

    private void deleteCustomerCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter customer ID: ");

        Long ID = scanner.nextLong();

        try{
            commonService.deleteCustomer(ID).thenAccept(result -> {
                if (result.contains("ERROR")) System.err.println(result);
                else System.out.println(result);
            });
        }
        catch (CommonServiceException e){
            System.out.println(e.getMessage());
        }

    }

    private void updateCustomerCommand() {
        Long id = readId();
        Customer newCustomer = readCustomer();
        newCustomer.setId(id);

        try{
            commonService.updateCustomer(newCustomer).thenAccept(result -> {
                if (result.contains("ERROR")) System.err.println(result);
                else System.out.println(result);
            });
        }
        catch (CommonServiceException e){
            System.out.println(e.getMessage());
        }
    }

    private void listCustomersCommand() {
        try{
            commonService.listCustomers().thenAccept(result -> {
                if (result.contains("ERROR")) System.err.println(result);
                else System.out.println(result);
            });
        }
        catch (CommonServiceException e){
            System.out.println(e.getMessage());
        }
    }


    private void addBookCommand() {
        Book book = readBook();

        try{
            commonService.addBook(book).thenAccept(result -> {
                if (result.contains("ERROR")) System.err.println(result);
                else System.out.println(result);
            });
        }
        catch (CommonServiceException e){
            System.out.println(e.getMessage());
        }

    }

    private void deleteBookCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter book ID: ");

        Long ID = scanner.nextLong();

        try{
            commonService.deleteBook(ID).thenAccept(result -> {
                if (result.contains("ERROR")) System.err.println(result);
                else System.out.println(result);
            });
        }
        catch (CommonServiceException e){
            System.out.println(e.getMessage());
        }

    }

    private void updateBookCommand() {
        Long id = readId();
        Book newBook = readBook();
        newBook.setId(id);

        try{
            commonService.updateBook(newBook).thenAccept(result -> {
                if (result.contains("ERROR")) System.err.println(result);
                else System.out.println(result);
            });
        }
        catch (CommonServiceException e){
            System.out.println(e.getMessage());
        }

    }

    private void listBooksCommand() {
        try{
            commonService.listBooks().thenAccept(result -> {
                if (result.contains("ERROR")) System.err.println(result);
                else System.out.println(result);
            });
        }
        catch (CommonServiceException e){
            System.out.println(e.getMessage());
        }
    }

    private void addTransactionCommand() {
        Transaction transaction = readTransaction();

        try{
            commonService.addTransaction(transaction).thenAccept(result -> {
                if (result.contains("ERROR")) System.err.println(result);
                else System.out.println(result);
            });
        }
        catch (CommonServiceException e){
            System.out.println(e.getMessage());
        }
    }

    private void deleteTransactionCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter transaction ID: ");

        Long ID = scanner.nextLong();

        try{
            commonService.deleteTransaction(ID).thenAccept(result -> {
                if (result.contains("ERROR")) System.err.println(result);
                else System.out.println(result);
            });
        }
        catch (CommonServiceException e){
            System.out.println(e.getMessage());
        }

    }

    private void updateTransactionCommand() {
        Long id = readId();
        Transaction newTransaction = readTransaction();
        newTransaction.setId(id);

        try{
            commonService.updateTransaction(newTransaction).thenAccept(result -> {
                if (result.contains("ERROR")) System.err.println(result);
                else System.out.println(result);
            });
        }
        catch (CommonServiceException e){
            System.out.println(e.getMessage());
        }

    }

    private void listTransactionsCommand() {
        try {
            commonService.listTransactions().thenAccept(result -> {
                if (result.contains("ERROR")) System.err.println(result);
                else System.out.println(result);
            });
        }
        catch (CommonServiceException e){
            System.out.println(e.getMessage());
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
            return new Customer(firstName, lastName, age);
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

            return new Book(isbn, title, author, publishingHouse, year, price);
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

            return new Transaction(customerId, bookId);
        }
        catch (RuntimeException e){
            throw new BookStoreException("BookStore Exception -> Invalid ID: ID is not a number");
        }
    }

    // TODO: Also add filters
    private Map<String, Runnable> getCommands() {
        Map<String, Runnable> commands = new HashMap<>();

        commands.put("0", this::exitCommand);
        commands.put("11", this::addCustomerCommand);
        commands.put("12", this::deleteCustomerCommand);
        commands.put("13", this::updateCustomerCommand);
        commands.put("14", this::listCustomersCommand);

        commands.put("21", this::addBookCommand);
        commands.put("22", this::deleteBookCommand);
        commands.put("23", this::updateBookCommand);
        commands.put("24", this::listBooksCommand);

        commands.put("31", this::addTransactionCommand);
        commands.put("32", this::deleteTransactionCommand);
        commands.put("33", this::updateTransactionCommand);
        commands.put("34", this::listTransactionsCommand);

        return commands;
    }

    private String getMenu() {

        String menu = "MENU\n" + "11. add customer\n" +
                "12. delete customer\n" +
                "13. update customer\n" +
                "14. list customers\n" +
                "21. add book\n" +
                "22. delete book\n" +
                "23. update book\n" +
                "24. list books\n" +
                "31. add transaction\n" +
                "32. delete transaction\n" +
                "33. update transaction\n" +
                "34. list transactions\n" +
                "0. EXIT\n";
        return menu;
    }
}
