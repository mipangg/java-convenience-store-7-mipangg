package store.util;

import java.util.List;
import store.domain.Item;
import store.repository.ItemRepository;

public class FakeItemRepository extends ItemRepository {
    private List<Item> items;

    @Override
    public void save(Item item) {
        super.save(item);
    }

    @Override
    public List<Item> findByName(String name) {
        return super.findByName(name);
    }

    @Override
    public void update(Item item, int soldAmount) {
        super.update(item, soldAmount);
    }

    @Override
    public List<Item> findAll() {
        return super.findAll();
    }
}
