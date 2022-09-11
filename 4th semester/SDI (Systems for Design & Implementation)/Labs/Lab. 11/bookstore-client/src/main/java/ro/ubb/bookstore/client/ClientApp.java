package ro.ubb.bookstore.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;
import ro.ubb.bookstore.client.view.Console;
import ro.ubb.bookstore.web.dto.BookDto;
import ro.ubb.bookstore.web.dto.BooksDto;

import java.util.concurrent.TimeUnit;

public class ClientApp {
    public static void main(String[] args) throws InterruptedException {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "ro.ubb.bookstore.client.config"
                );
        RestTemplate restTemplate = context.getBean(RestTemplate.class);
        Console console = context.getBean(Console.class);

        TimeUnit.SECONDS.sleep(1);

        console.start();
    }
}
