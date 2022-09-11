package ro.ubb.bookstore.core.model;

public class Pair<T, T2> {
    private T first;
    private T2 second;

    public Pair(T first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T2 getSecond() {
        return second;
    }

    public void setSecond(T2 second) {
        this.second = second;
    }
}
