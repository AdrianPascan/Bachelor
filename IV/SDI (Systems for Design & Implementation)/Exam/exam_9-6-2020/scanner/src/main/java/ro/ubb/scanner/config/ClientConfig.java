package ro.ubb.scanner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.ubb.scanner.console.Console;
import ro.ubb.scanner.repository.ScannerClientRepository;
import ro.ubb.scanner.service.ScannerClientService;
import ro.ubb.scanner.tcp.TcpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ClientConfig {
    @Bean
    Console console() {
        return new Console();
    }

    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    TcpClient tcpClient() {
        return new TcpClient();
    }

    @Bean
    ScannerClientService scannerClientService() {
        return new ScannerClientService();
    }

    @Bean
    ScannerClientRepository scannerClientRepository() {
        return new ScannerClientRepository();
    }
}
