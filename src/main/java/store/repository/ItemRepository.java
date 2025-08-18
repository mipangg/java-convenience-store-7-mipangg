package store.repository;

import java.util.ArrayList;
import java.util.List;
import store.domain.Item;

public class ItemRepository {
    private final List<Item> items = new ArrayList<>();

    public void save(Item item) {
        items.add(item);
    }

    public List<Item> findByName(String name) {
        List<Item> targetItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(name)) {
                targetItems.add(item);
            }
        }
        return targetItems;
    }

    public int update(String name, int soldAmount) {
        Item targetItems = findByName(name).getFirst();
        return targetItems.updateStock(soldAmount);
    }

    public List<Item> findAll() {
        return new ArrayList<>(items);
    }
}
