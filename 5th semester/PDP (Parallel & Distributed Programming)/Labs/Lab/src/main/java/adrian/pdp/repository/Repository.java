package adrian.pdp.repository;

import java.util.List;

public interface Repository<T> {
    void addItem(T item);
    void addItems(List<T> items);
    List<T> getItems();
}
