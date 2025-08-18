package store.service;

import java.util.List;
import java.util.Map;
import store.domain.Item;
import store.exception.ErrorCode;
import store.repository.ItemRepository;

public class ItemService {
    private final ItemRepository itemRepository;

    ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void save(List<Map<String, String>> itemInfos) {
        for (Map<String, String> itemInfo : itemInfos) {
            itemRepository.save(new Item(itemInfo));
        }
    }

    public List<Item> getByName(String name) {
        List<Item> items = itemRepository.findByName(name);
        if (items.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
        }
        return items;
    }

    // 차감 후 남은 재고수량 반환
    public int updateStock(Item item, int soldAmount) {
        return itemRepository.update(item, soldAmount);
    }

}
