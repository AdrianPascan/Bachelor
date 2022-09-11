package ro.ubb.springjpa;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.springjpa.ui.Console;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.springjpa");

        context.getBean(Console.class).start();
    }
}
