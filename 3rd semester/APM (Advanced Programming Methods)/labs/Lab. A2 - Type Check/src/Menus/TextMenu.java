package Menus;

import Commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu(){
        commands = new HashMap<>();
    }

    public void addCommand(Command command){
        commands.put(command.getKey(), command);
    }

    private void printMenu(){
        for (Command command: commands.values()){
            String line = String.format("%4s: %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);

        while (true){
            printMenu();
            System.out.printf("\t >>> ");
            String key = scanner.nextLine();
            Command command = commands.get(key);
            if (command == null){
                System.out.println("Invalid option.\n");
                continue;
            }
            command.execute();
        }
    }
}
