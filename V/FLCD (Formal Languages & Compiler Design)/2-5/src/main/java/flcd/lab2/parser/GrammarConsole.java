package flcd.lab2.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GrammarConsole {
    private Grammar grammar;

    public GrammarConsole(Grammar grammar) {
        this.grammar = grammar;
    }

    public void start() {
        String command = "";
        Map<String, Runnable> commands = getCommands();
        String menu = getMenu();
        Scanner scanner = new Scanner(System.in);

        while (!command.equals("0")) {
            System.out.println(menu);
            try {
                command = readLine();
                commands.get(command).run();
            } catch (NullPointerException npe) {
                System.out.println("Invalid command..");
            }
        }
    }

    private String readLine() {
        System.out.println("\t >> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void nonterminalsCommand() {
        System.out.println("\t" + grammar.getNonterminals());
    }

    private void terminalsCommand() {
        System.out.println("\t" + grammar.getTerminals());
    }

    private void productionsCommand() {
        System.out.println("Nonterminal:");
        String nonterminal = readLine();

        List<List<String>> productions = grammar.getProductions().get(nonterminal);

        if (productions == null) {
            System.out.println("No productions for '" + nonterminal + "'..");
        }
        else {
            System.out.println(nonterminal + "->");

            productions.forEach(production -> System.out.println("\t" + production));
        }
    }

    private void startSymbolCommand() {
        System.out.println(grammar.getStartSymbol());
    }

    private void exitCommand() {
        System.out.println("Closing Grammar Console..");
        System.out.println("Grammar Console has been closed.");
    }

    private Map<String, Runnable> getCommands() {
        Map<String, Runnable> commands = new HashMap<>();

        commands.put("0", this::exitCommand);
        commands.put("1", this::nonterminalsCommand);
        commands.put("2", this::terminalsCommand);
        commands.put("3", this::productionsCommand);
        commands.put("4", this::startSymbolCommand);

        return commands;
    }

    private String getMenu() {
        return "________________________________________\n" +
                "Grammar Menu:\n" +
                "1. nonterminals\n" +
                "2. terminals\n" +
                "3. productions\n" +
                "4. start symbol\n" +
                "0. EXIT\n";
    }
}
