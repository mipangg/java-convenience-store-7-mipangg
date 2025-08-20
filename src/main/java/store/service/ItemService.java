package store.service;

import java.util.List;
import java.util.Map;
import store.domain.Item;
import store.domain.Promotion;
import store.exception.ErrorCode;
import store.mapper.StoreMapper;
import store.repository.ItemRepository;

public class ItemService {
    private final ItemRepository itemRepository;
    private final PromotionService promotionService;
    private final StoreMapper storeMapper;

    public ItemService(
            ItemRepository itemRepository,
            PromotionService promotionService,
            StoreMapper storeMapper
    ) {
        this.itemRepository = itemRepository;
        this.promotionService = promotionService;
        this.storeMapper = storeMapper;
    }

    public void saveItem(Map<String, String> itemInfo) {
        Promotion promotion = promotionService.getPromotion(itemInfo.get("promotion"));
        itemRepository.save(storeMapper.toItem(itemInfo, promotion));
    }

    public List<Item> getByName(String name) {
        List<Item> items = itemRepository.findByName(name);
        if (items.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.ITEM_NOT_FOUND.getMessage());
        }
        return items;
    }

    public List<Item> getAll() {
        List<Item> allItems = itemRepository.findAll();
        if (allItems.isEmpty()) {
            throw new IllegalStateException(ErrorCode.EMPTY_ITEM_LIST.getMessage());
        }
        return allItems;
    }

}
