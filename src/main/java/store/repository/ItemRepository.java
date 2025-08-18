package store.repository;

import java.util.ArrayList;
import java.util.List;
import store.domain.Item;

public class ItemRepository {
    private final List<Item> items = new ArrayList<>();

    public void save(Item item) {
        items.add(item);
    }

    public List<Item> findAll() {
        return new ArrayList<>(items);
    }
}
