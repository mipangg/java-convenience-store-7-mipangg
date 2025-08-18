package store.util;

import java.util.ArrayList;
import java.util.List;
import store.domain.Item;
import store.repository.ItemRepository;

public class FakeItemRepository extends ItemRepository {
    List<Item> items = new ArrayList<>();

    @Override
    public void save(Item item) {
        items.add(item);
    }

    @Override
    public List<Item> findByName(String name) {
        List<Item> targetItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(name)) {
                targetItems.add(item);
            }
        }
        return targetItems;
    }

    @Override
    public void update(Item item, int soldAmount) {
        List<Item> targetItems = findByName(item.getName());
        for (Item targetItem : targetItems) {
            if (targetItem.getPromotion().equals(item.getPromotion())) {
                targetItem.updateStock(soldAmount);
            }
        }
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(items);
    }
}
