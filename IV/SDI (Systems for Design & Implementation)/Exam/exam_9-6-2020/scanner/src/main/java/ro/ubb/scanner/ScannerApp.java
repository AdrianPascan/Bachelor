package ro.ubb.scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.scanner.console.Console;

import java.util.concurrent.ExecutorService;

public class ScannerApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.scanner.config");

        Console console = (Console) context.getBean("console");
        ExecutorService executorService = (ExecutorService) context.getBean("executorService");

        console.start();
        executorService.shutdown();
    }
}
