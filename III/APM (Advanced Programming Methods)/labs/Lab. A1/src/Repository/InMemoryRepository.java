package Repository;

import Exceptions.MyException;
import Model.Vehicle;

public class InMemoryRepository implements RepositoryInterface {
    private int numberOfElements = 0;
    private int capacity;
    private Vehicle[] data;

    public InMemoryRepository(int capacity){
        this.capacity = capacity;
        data = new  Vehicle[capacity];
    }

    @Override
    public void store(Vehicle vehicle) throws MyException {
        if (numberOfElements == capacity){
            throw new MyException("Repository error: maximum capacity reached");
        }
        data[numberOfElements++] = vehicle;
    }

    @Override
    public void delete(String name) throws MyException {
        boolean deleted = false;

        for (int index = 0; index < numberOfElements && !deleted; index++) {
            if (data[index].getName().equals(name)) {
                if (index != numberOfElements - 1) {
                    data[index] = data[numberOfElements-1];
                }
                numberOfElements--;
                deleted = true;
            }
        }

        if (!deleted){
            throw new MyException("Repository error: non-existing element");
        }
    }

    @Override
    public Vehicle[] getAll(String filterBy, String value) throws MyException{
        if (filterBy.isEmpty()){
            return getCopy();
        }
        else if (filterBy.equals("color")){
            return getCopyByColor(value);
        }
        throw new MyException("RepositoryError: invalid filter");
    }

    private Vehicle[] getCopy(){
        Vehicle[] copy = new Vehicle[numberOfElements];
        for (int currentIndex = 0; currentIndex < numberOfElements; currentIndex++){
            copy[currentIndex] = data[currentIndex];
        }
        return copy;
    }

    private Vehicle[] getCopyByColor(String color){
        int numberOfMatching = 0;
        for (int currentIndex = 0; currentIndex < numberOfElements; currentIndex++) {
            if (data[currentIndex].getColor().equals(color)){
                numberOfMatching++;
            }
        }

        Vehicle[] matching = new Vehicle[numberOfMatching];

        for (int currentIndex = 0, currentMatchingIndex = 0; currentMatchingIndex < numberOfMatching; currentIndex++){
            if (data[currentIndex].getColor().equals(color)){
                matching[currentMatchingIndex++] = data[currentIndex];
            }
        }

        return matching;
    }
}
