package flcd.lab2.automaton;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FAConsole {
    private FA fa;

    public FAConsole(FA fa) {
        this.fa = fa;
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

    private void statesCommand() {
        System.out.println("\t" + fa.getStates());
    }

    private void alphabetCommand() {
        System.out.println("\t" + fa.getAlphabet());
    }

    private void transitionsCommand() {
        fa.getTransitions().forEach((transitionInput, transitionOutput) -> {
            transitionOutput.getStates().forEach(output -> {
                System.out.println(String.format(
                        "\tdelta(%s, %s)= %s",
                        transitionInput.getState(), transitionInput.getSymbol(), output
                ));
            });
        });
    }

    private void initialStateCommand() {
        System.out.println(fa.getInitialState());
    }

    private void finalStatesCommand() {
        System.out.println("\t" + fa.getFinalStates());
    }

    private void isAcceptedCommand() {
        System.out.println("Sequence:");
        String sequence = readLine();

        try {
            System.out.println((fa.isAccepted(sequence) ? "" : "NOT ") + "accepted");
        } catch (FAException fae) {
            System.out.println(fae.getMessage());
        }
    }

    private void exitCommand() {
        System.out.println("Closing FA Console..");
        System.out.println("FA Console has been closed.");
    }

    private Map<String, Runnable> getCommands() {
        Map<String, Runnable> commands = new HashMap<>();

        commands.put("0", this::exitCommand);
        commands.put("1", this::statesCommand);
        commands.put("2", this::alphabetCommand);
        commands.put("3", this::transitionsCommand);
        commands.put("4", this::initialStateCommand);
        commands.put("5", this::finalStatesCommand);
        commands.put("6", this::isAcceptedCommand);

        return commands;
    }

    private String getMenu() {
        return "________________________________________\n" +
                "FA Menu:\n" +
                "1. states\n" +
                "2. alphabet\n" +
                "3. transitions\n" +
                "4. initial state\n" +
                "5. final states\n" +
                "6. check if sequence is accepted by DFA\n" +
                "0. EXIT\n";
    }
}
