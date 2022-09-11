package ro.ubb.remoting.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import ro.ubb.remoting.common.BookService;
import ro.ubb.remoting.common.CustomerService;
import ro.ubb.remoting.common.Service;
import ro.ubb.remoting.common.TransactionService;
import ro.ubb.remoting.common.domain.Transaction;
import ro.ubb.remoting.server.repository.BookRepository;
import ro.ubb.remoting.server.repository.CustomerRepository;
import ro.ubb.remoting.server.repository.TransactionRepository;
import ro.ubb.remoting.server.service.BookServerService;
import ro.ubb.remoting.server.service.CustomerServerService;
import ro.ubb.remoting.server.service.ServerService;
import ro.ubb.remoting.server.service.TransactionServerService;

@Configuration
public class ServerConfig {
    @Bean
    RmiServiceExporter rmiServiceExporterCustomer() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("customerService");
        rmiServiceExporter.setServiceInterface(CustomerService.class);
        rmiServiceExporter.setService(customerService());
        return rmiServiceExporter;
    }

    @Bean
    RmiServiceExporter rmiServiceExporterBook() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("bookService");
        rmiServiceExporter.setServiceInterface(BookService.class);
        rmiServiceExporter.setService(bookService());
        return rmiServiceExporter;
    }

    @Bean
    RmiServiceExporter rmiServiceExporterTransaction() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("transactionService");
        rmiServiceExporter.setServiceInterface(TransactionService.class);
        rmiServiceExporter.setService(transactionService());
        return rmiServiceExporter;
    }

    @Bean
    CustomerService customerService() {
        return new CustomerServerService(customerRepository(), transactionRepository());
    }

    @Bean
    BookService bookService() {
        return new BookServerService(bookRepository(), transactionRepository());
    }

    @Bean
    TransactionService transactionService() {
        return new TransactionServerService(transactionRepository(), bookRepository());
    }

    @Bean
    CustomerRepository customerRepository() {
        return new CustomerRepository();
    }

    @Bean
    BookRepository bookRepository() {
        return new BookRepository();
    }

    @Bean
    TransactionRepository transactionRepository() {
        return new TransactionRepository();
    }
}
