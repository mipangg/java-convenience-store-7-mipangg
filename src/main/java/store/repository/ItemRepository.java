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
        return items.stream()
                .filter(item -> item.getName().equals(name))
                .toList();
    }

    public List<Item> findAll() {
        return new ArrayList<>(items);
    }

    // 차감 후 업데이트 된 재고 반환
    public int update(Item item, int soldAmount) {
        return item.updateStock(soldAmount);
    }
}
