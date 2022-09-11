package Model.ProgramState;

import Model.DataStructures.MyListInterface;
import Model.Values.Value;

import java.util.ArrayList;

public class Output implements MyListInterface<Value> {
    private ArrayList<Value> list;

    public Output() {
        list = new ArrayList<Value>();
    }

    @Override
    public void add(Value value) {
        list.add(value);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Output:\n");
        for (Value value: list){
            result.append(value.toString());
            result.append("\n");
        }
        return result.toString();
    }
}
