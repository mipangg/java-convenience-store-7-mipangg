package store.service;

import java.util.Map;
import store.mapper.StoreMapper;
import store.repository.PromotionRepository;

public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final StoreMapper storeMapper;

    public PromotionService(PromotionRepository promotionRepository, StoreMapper storeMapper) {
        this.promotionRepository = promotionRepository;
        this.storeMapper = storeMapper;
    }

    public void createPromotion(Map<String, String> promotionInfo) {
        promotionRepository.save(storeMapper.toPromotion(promotionInfo));
    }
}
