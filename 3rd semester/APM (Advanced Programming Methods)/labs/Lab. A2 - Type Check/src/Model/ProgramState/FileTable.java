package Model.ProgramState;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.util.HashMap;

public class FileTable implements MyDictionaryInterface<StringValue, BufferedReader> {
    private HashMap<StringValue, BufferedReader> table;

    public FileTable(){
        table = new HashMap<StringValue, BufferedReader>();
    }

    @Override
    public boolean isDefined(StringValue id) {
        return table.containsKey(id);
    }

    @Override
    public BufferedReader getValue(StringValue id) {
        return table.get(id);
    }

    @Override
    public void update(StringValue id, BufferedReader value) {
        table.put(id, value);
    }

    @Override
    public BufferedReader lookup(StringValue id) throws MyException {
        if (!table.containsKey(id)){
            throw new MyException("File " + id + " does not exist.");
        }
        return table.get(id);
    }

    @Override
    public BufferedReader remove(StringValue id) throws MyException {
        BufferedReader reader = table.remove(id);

        if (reader == null) {
            throw new MyException("File " + id + " does not exist.");
        }

        return reader;
    }

    @Override
    public MyDictionaryInterface<StringValue, BufferedReader> getClone() {
        MyDictionaryInterface<StringValue, BufferedReader> clone = new FileTable();
        for (StringValue key : table.keySet()) {
            clone.update(key, table.get(key));
        }
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("File table:\n");
        for (StringValue key : table.keySet()){
            result.append(key);
            result.append("\n");
        }
        return result.toString();
    }
}
