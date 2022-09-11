package Model.ProgramState;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable implements MyDictionaryInterface<String, Value> {
    private HashMap<String, Value> table;

    public SymbolTable() {
        table = new HashMap<String, Value>();
    }

    public SymbolTable(HashMap<String, Value> table) {
        this.table = table;
    }

    public Map<String, Value> getContent() {
        return table;
    }

    @Override
    public boolean isDefined(String id) {
        return table.containsKey(id);
    }

    @Override
    public Value getValue(String id) {
        return table.get(id);
    }

    @Override
    public void update(String id, Value value) {
        table.put(id, value);
    }

    @Override
    public Value lookup(String id) throws MyException{
        if (!table.containsKey(id)){
            throw new MyException("Variable " + id + "is not defined.");
        }
        return table.get(id);
    }

    @Override
    public Value remove(String id) throws MyException {
        Value value = table.remove(id);

        if (value == null){
            throw new MyException("Variable " + id + " does not exist.");
        }

        return value;
    }

    public MyDictionaryInterface<String, Value> getClone() {
        MyDictionaryInterface<String, Value> clone = new SymbolTable();
        for (String key : table.keySet()) {
            clone.update(key, table.get(key));
        }
        return clone;
//        return new SymbolTable((HashMap<String, Value>) table.clone());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Symbol table:\n");
        for (String key: table.keySet()){
            result.append(key).append(" ---> ").append(table.get(key).toString());
            result.append("\n");
        }
        return result.toString();
    }


}
