package ro.ubb.bookstore.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ro.ubb.bookstore.client.view.Console;

@Configuration
@ComponentScan({"ro.ubb.bookstore.client", "ro.ubb.bookstore.core.model.validators", "ro.ubb.bookstore.web.converter"})
public class ClientConfig {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    Console console() {
        return new Console();
    }
}
