package adrian.pdp.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RepositoryImpl<T> implements Repository<T> {
    private List<T> items;

    public RepositoryImpl() {
        items = new ArrayList<>();
    }

    @Override
    public void addItem(T item) {
        items.add(item);
    }

    @Override
    public void addItems(List<T> items) {
        this.items.addAll(items);
    }

    @Override
    public List<T> getItems() {
        return items;
    }
}
