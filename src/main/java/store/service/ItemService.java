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
    public int updateStock(String name, int soldAmount) {
        // 프로모션 적용 가능한지 확인
        // 프로모션 적용 조건 확인
        // 적용 불가능하면 일반 재고에서 차감
        return itemRepository.update(name, soldAmount);
    }

    public List<Item> getAll() {
        List<Item> allItems = itemRepository.findAll();
        if (allItems.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.OUT_OF_ALL_STOCK.getMessage());
        }
        return allItems;
    }
}
