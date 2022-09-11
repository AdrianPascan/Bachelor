package Model.ProgramState;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Types.Type;

import java.util.HashMap;
import java.util.Map;

public class TypeEnvironment implements MyDictionaryInterface<String, Type> {
    Map<String, Type> table;

    TypeEnvironment() {
        table = new HashMap<String, Type>();
    }

    @Override
    public boolean isDefined(String id) {
        return table.containsKey(id);
    }

    @Override
    public Type getValue(String id) {
        return table.get(id);
    }

    @Override
    public void update(String id, Type value) {
        table.put(id, value);
    }

    @Override
    public Type lookup(String id) throws MyException {
        if (!table.containsKey(id)) {
            throw new MyException("Variable " + id + " does not exist in type environment.");
        }
        return table.get(id);
    }

    @Override
    public Type remove(String id) throws MyException {
        Type type = table.remove(id);

        if (type == null) {
            throw new MyException("Variable " + id + " does not exist in type environment.");
        }

        return type;
    }

    @Override
    public MyDictionaryInterface<String, Type> getClone() {
        MyDictionaryInterface<String, Type> clone = new TypeEnvironment();
        for (String key : table.keySet()) {
            clone.update(key, table.get(key));
        }
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Type environment:\n");

        for (String key: table.keySet()){
            result.append(key + " ---> " + table.get(key) + "\n");
        }

        return result.toString();
    }
}
