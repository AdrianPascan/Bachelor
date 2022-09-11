package Model.ProgramState;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Heap implements MyDictionaryInterface<Integer, Value> {
    private Map<Integer, Value> table;
    private int freeAddress;

    public Heap() {
        table = new ConcurrentHashMap<Integer, Value>();
        freeAddress = 1;
    }

    public Map<Integer, Value> getContent() {
        return table;
    }

    public void setContent(Map<Integer, Value> newTable) {
        table = newTable;
    }

    public int getFreeAddress() {
        return freeAddress;
    }

    @Override
    public boolean isDefined(Integer id) {
        return table.containsKey(id);
    }

    @Override
    public Value getValue(Integer id) {
        return table.get(id);
    }

    @Override
    public void update(Integer id, Value value) {
        table.put(id, value);
        freeAddress++;
    }

    @Override
    public Value lookup(Integer id) throws MyException {
        if (! table.containsKey(id) ) {
            throw new MyException("Address " + id.toString() + " is invalid or free (it does not reference any value).");
        }
        return table.get(id);
    }

    @Override
    public Value remove(Integer id) throws MyException {
        Value value = table.remove(id);

        if (value == null){
            throw new MyException("Address " + id.toString() + " is free (it does not reference any value).");
        }

        return value;
    }

    @Override
    public MyDictionaryInterface<Integer, Value> getClone() {
        MyDictionaryInterface<Integer, Value> clone = new Heap();
        for (Integer key : table.keySet()) {
            clone.update(key, table.get(key));
        }
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Heap:\n");
        for (int address: table.keySet()) {
            result.append(Integer.toString(address)).append(" ---> ").append(table.get(address).toString());
            result.append("\n");
        }
        return result.toString();
    }
}
