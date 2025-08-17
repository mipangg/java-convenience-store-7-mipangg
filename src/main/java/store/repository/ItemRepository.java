package store.repository;

import java.util.ArrayList;
import java.util.List;
import store.domain.Item;

public class ItemRepository {
    List<Item> items = new ArrayList<>();

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

    public void update(Item item, int soldAmount) {
        List<Item> targetItems = findByName(item.getName());
        for (Item targetItem : targetItems) {
            if (targetItem.getPromotion().equals(item.getPromotion())) {
                targetItem.updateStock(soldAmount);
            }
        }
    }

    public List<Item> findAll() {
        return new ArrayList<>(items);
    }
}
