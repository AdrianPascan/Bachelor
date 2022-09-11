package Model.ProgramState;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LockTable implements MyDictionaryInterface<Integer, Integer> {
    private Map<Integer, Integer> table;
    private int freeAddress;

    public LockTable() {
        table = new ConcurrentHashMap<Integer, Integer>();
        freeAddress = 1;
    }

    public Map<Integer, Integer> getContent(){
        return table;
    }

    public int getFreeAddress() {
        return freeAddress;
    }

    public int getFreeAddressAndComputeNext() {
        return freeAddress++;
    }

    @Override
    public boolean isDefined(Integer id) {
        return table.containsKey(id);
    }

    @Override
    public Integer getValue(Integer id) {
        return table.get(id);
    }

    @Override
    public void update(Integer id, Integer value) {
        table.put(id, value);
    }

    @Override
    public Integer lookup(Integer id) throws MyException {
        if (! table.containsKey(id) ) {
            throw new MyException(id.toString() + " is not a key in lock table.");
        }
        return table.get(id);
    }

    @Override
    public Integer remove(Integer id) throws MyException {
        Integer value = table.remove(id);

        if (value == null){
            throw new MyException("There is no key " + id + " in lock table.");
        }

        return value;
    }

    @Override
    public MyDictionaryInterface<Integer, Integer> getClone() {
        MyDictionaryInterface<Integer, Integer> clone = new LockTable();
        for (Integer key : table.keySet()) {
            clone.update(key, table.get(key));
        }
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Lock table:\n");
        for (int key: table.keySet()) {
            result.append(Integer.toString(key)).append(" ---> ").append(Integer.toString(table.get(key)));
            result.append("\n");
        }
        return result.toString();
    }
}
