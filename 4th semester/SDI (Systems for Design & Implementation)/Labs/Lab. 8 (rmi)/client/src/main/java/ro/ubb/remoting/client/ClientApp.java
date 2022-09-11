package ro.ubb.remoting.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.remoting.client.view.Console;
import ro.ubb.remoting.common.Service;

public class ClientApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "ro.ubb.remoting.client.config"
                );

        Console console = context.getBean(Console.class);
        console.start();
    }
}
