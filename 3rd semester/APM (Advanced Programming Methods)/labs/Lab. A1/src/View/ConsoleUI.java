package View;

import Controller.Controller;
import Exceptions.MyException;
import Model.Bycicle;
import Model.Car;
import Model.Motorcycle;
import Model.Vehicle;

import java.util.Scanner;

public class ConsoleUI {
    private Controller controller;
    private static Scanner scanner = new Scanner(System.in);

    public ConsoleUI(Controller controller){
        this.controller = controller;
    }

    public void start(){
        String command = "";

        while (!command.equals("0")) {
            printMenu();

            try {
                command = readCommand();

                if (command.equals("0")) {
                    exit();
                }
                else if (command.equals("1")) {
                    add();
                }
                else if (command.equals("2")) {
                    delete();
                }
                else if (command.equals("3")){
                    printAll("");
                }
                else if (command.equals("4")) {
                    printAll("color");
                }
                else {
                    System.out.println("Invalid command");
                }
            }
            catch(MyException exception){
                System.out.println(exception);
            }
            finally {
                System.out.println("__________________________________________________");
            }
        }
    }

    public void populate(){
        Bycicle bycicle = new Bycicle("Pegas", "red");
        Car car1 = new Car("Bugatti", "black");
        Car car2 = new Car("Audi", "gray");
        Motorcycle motorcycle = new Motorcycle("Harley", "black");

        try {
            controller.add(bycicle);
            controller.add(car1);
            controller.add(car2);
            controller.add(motorcycle);
        }
        catch (MyException exception) {}
    }

    private void exit(){
        System.out.println("Closing application..");
        System.out.println("Application has been closed..");
    }

    private void add() throws MyException{
        Vehicle vehicle = readVehicle();
        controller.add(vehicle);

        System.out.println("Vehicle " + vehicle.getName() + " was added");
    }

    private void delete() throws  MyException{
        String name = readString("name");
        controller.delete(name);

        System.out.println("Vehicle " + name + " was deleted");
    }

    private void printAll(String filterBy) throws MyException{
        String result;
        if (filterBy.isEmpty()){
            result = controller.getAll("","");
        }
        else{
            String value = readString(filterBy);
            result = controller.getAll(filterBy, value);
        }

        if (result.isEmpty()){
            System.out.println("No matching vehicles");
            return;
        }
        System.out.print(result);
    }

    private static void printMenu() {
        System.out.println("Available commands:");
        System.out.println(" 1. add vehicle");
        System.out.println(" 2. delete vehicle");
        System.out.println(" 3. get all vehicles");
        System.out.println(" 4. get vehicles by color");
        System.out.println(" 0. exit");
    }

    private static String readCommand(){
        System.out.print(">>> ");
        return scanner.nextLine();
    }

    private static Vehicle readVehicle() throws MyException{
        String name = readString("name");
        String color = readString("color");
        String type = readString("type");

        if (type.equals("bycicle")){
            return new Bycicle(name, color);
        }
        else if (type.equals("car")) {
            return new Car(name, color);
        }
        else if (type.equals("motorcycle")) {
            return new Motorcycle(name, color);
        }
        else{
            throw new MyException("Invalid type of vehicle");
        }
    }

    private static String readString (String message){
        System.out.print(message + "\t>> ");
        return scanner.nextLine();
    }
}
