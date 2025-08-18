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
    public int update(String name, int soldAmount) {
        Item targetItems = findByName(name).getFirst();
        return targetItems.updateStock(soldAmount);
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(items);
    }
}
