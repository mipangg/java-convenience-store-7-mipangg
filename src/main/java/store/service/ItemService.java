package store.service;

import java.util.List;
import java.util.Map;
import store.domain.Item;
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

}
