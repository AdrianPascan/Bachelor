package Model.DataStructures;

import Exceptions.MyException;

import java.util.Map;

public interface MyDictionaryInterface <A,B> {
    boolean isDefined(A id);
    B getValue(A id);
    void update(A id, B value);
    B lookup(A id) throws MyException;
    B remove(A id) throws MyException;
    MyDictionaryInterface<A,B> getClone();
}
