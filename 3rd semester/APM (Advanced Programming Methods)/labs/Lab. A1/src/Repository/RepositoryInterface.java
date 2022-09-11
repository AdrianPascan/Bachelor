package Repository;

import Exceptions.MyException;
import Model.Vehicle;

import java.lang.reflect.Array;

public interface RepositoryInterface {
    public void store(Vehicle vehicle) throws MyException;
    public void delete(String name) throws MyException;
    public Vehicle[] getAll(String filterBy, String value) throws MyException;
}
