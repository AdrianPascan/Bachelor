package flcd.lab2;


import flcd.lab2.parser.Grammar;
import flcd.lab2.parser.GrammarConsole;
import flcd.lab2.parser.Parser;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("APP STARTED..\n");

        Parser parser = new Parser("src/main/java/flcd/lab2/parser/input/g1.txt");
        System.out.println(parser.isAccepted(Arrays.asList("a", "b")));

        System.out.println("\nAPP STOPPED");
    }
}
