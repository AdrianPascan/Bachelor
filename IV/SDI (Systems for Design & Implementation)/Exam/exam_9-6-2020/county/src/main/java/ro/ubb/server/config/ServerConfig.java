package ro.ubb.server.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;
import ro.ubb.server.console.ConsoleServer;
import ro.ubb.server.controller.ServiceController;
import ro.ubb.server.repository.CountyRepository;
import ro.ubb.server.repository.ScannerRepository;
import ro.ubb.server.service.ServiceServer;
import ro.ubb.server.tcp.TcpServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan({"ro.ubb.monitorweb.converter", "ro.ubb.monitorweb.dto"})
public class ServerConfig {
    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    ServiceController serviceController() {
        return new ServiceController();
    }

    @Bean
    ServiceServer serviceServer() {
        return new ServiceServer();
    }

    @Bean
    TcpServer tcpServer() {
        return new TcpServer();
    }

    @Bean
    ConsoleServer consoleServer() {
        return new ConsoleServer();
    }

    @Bean
    CountyRepository countyRepository() {
        return new CountyRepository();
    }

    @Bean
    ScannerRepository scannerRepository() {
        return new ScannerRepository();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
