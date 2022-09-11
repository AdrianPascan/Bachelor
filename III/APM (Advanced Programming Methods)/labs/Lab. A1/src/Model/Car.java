package Model;

public class Car implements Vehicle {
    String color;
    String name;
    String type;

    public Car(String name, String color){
        this.name = name;
        this.color = color;
        this.type = "car";
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return type;
    }

    public String toString(){
        return type + ": " + name + " (" + color + ")";
    }
}
