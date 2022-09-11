package ro.ubb.scanner.console;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.scanner.model.ScannerClient;
import ro.ubb.scanner.service.ScannerClientService;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Console {
    @Autowired
    private ScannerClientService service;

    public void start() {
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

    private void addScannerCommand() {
        Scanner scanner = new Scanner(System.in);

        String name;
        Integer countyId;

        try {
            System.out.println("Scanner name:\n\t >> ");
            name = scanner.next();

            System.out.println("Scanner county-id (at least 1024) :\n\t >> ");
            countyId = scanner.nextInt();
        }
        catch (InputMismatchException exception) {
            throw new IllegalArgumentException("Invalid integer for county-id");
        }

        if (countyId < 1024) {
            throw new IllegalArgumentException("Id must be greater than 1024");
        }

        ScannerClient scannerClient = ScannerClient.builder()
                .name(name)
                .countyId(countyId)
                .build();

        service.addScanner(scannerClient);
    }

    private void exitCommand() {
        System.out.println("Closing Application...");
        System.out.println("Application has been closed.");
        System.exit(0);
    }

    private String getMenu() {
        StringBuilder menu = new StringBuilder("MENU\n");

        menu.append("1. Add Scanner\n");
        menu.append("0. EXIT\n");

        return menu.toString();
    }

    private Map<String, Runnable> getCommands() {
        Map<String, Runnable> commands = new HashMap<>();
        commands.put("0", this::exitCommand);
        commands.put("1", this::addScannerCommand);
        return commands;
    }
}
