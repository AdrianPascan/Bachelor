package Controller;

import Exceptions.MyException;
import Model.Vehicle;
import Repository.RepositoryInterface;

public class Controller {
    private RepositoryInterface repository;

    public Controller(RepositoryInterface repository){
        this.repository = repository;
    }

    public void add(Vehicle vehicle) throws MyException {
        repository.store(vehicle);
    }

    public void delete(String name) throws MyException {
        repository.delete(name);
    }

    public String getAll(String filterBy, String value) throws MyException{
        StringBuilder result = new StringBuilder();
        Vehicle[] vehicles = repository.getAll(filterBy, value);

        for (Vehicle current: vehicles){
                result.append(current).append("\n");
        }

        return result.toString();
    }
}
