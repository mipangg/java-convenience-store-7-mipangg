package store.service;

import java.util.Map;
import store.domain.Promotion;
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

}
