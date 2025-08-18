package store.service;

import java.util.Map;
import store.domain.Promotion;
import store.exception.ErrorCode;
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

    public Promotion getPromotion(String name) {
        Promotion promotion = promotionRepository.findByName(name);
        if (promotion == null) {
            throw new IllegalArgumentException(ErrorCode.PROMOTION_NOT_FOUND.getMessage());
        }
        return promotion;
    }
}
