package ro.ubb.server.console;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.server.model.CountyServer;
import ro.ubb.server.service.ServiceServer;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ConsoleServer {
    @Autowired
    private ServiceServer service;

    public void start() {
        System.out.println("CONSOLE STARTED");

        String menu = this.getMenu();
        Map<String, Runnable> commands = this.getCommands();
        String command = "";

        while (!command.equals("0")) {
            System.out.println(menu);

            System.out.println("\t >>> ");
            command = readCommand();

            try {
                commands.get(command).run();
            }
            catch (NullPointerException exception) {
                System.out.println("Invalid command...");
            }
            catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private String readCommand() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void addCountyCommand() {
        Scanner scanner = new Scanner(System.in);

        String name;
        Integer countyId;

        try {
            System.out.println("County name:\n\t >> ");
            name = scanner.next();

            System.out.println("County-id (at least 1024) :\n\t >> ");
            countyId = scanner.nextInt();
        }
        catch (InputMismatchException exception) {
            throw new IllegalArgumentException("Invalid integer for county-id");
        }

        if (countyId < 1024) {
            throw new IllegalArgumentException("County-id must be greater than 1024");
        }

        CountyServer countyServer = CountyServer.builder()
                .name(name)
                .countyId(countyId)
                .build();

        service.addCounty(countyServer);
    }

    private void exitCommand() {
        System.out.println("Closing Application...");
        System.out.println("Application has been closed.");
//        System.exit(0);
    }

    private String getMenu() {
        StringBuilder menu = new StringBuilder("MENU\n");

        menu.append("1. Add County\n");
        menu.append("0. EXIT\n");

        return menu.toString();
    }

    private Map<String, Runnable> getCommands() {
        Map<String, Runnable> commands = new HashMap<>();
        commands.put("0", this::exitCommand);
        commands.put("1", this::addCountyCommand);
        return commands;
    }
}
