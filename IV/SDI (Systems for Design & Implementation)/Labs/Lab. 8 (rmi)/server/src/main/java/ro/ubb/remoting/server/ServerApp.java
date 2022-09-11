package ro.ubb.remoting.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.remoting.common.Service;
import ro.ubb.remoting.common.domain.Book;
import ro.ubb.remoting.server.service.ServerService;

public class ServerApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=
                new AnnotationConfigApplicationContext(
                        "ro.ubb.remoting.server.config"
                );

        System.out.println("SERVER STARTED");
    }
}
