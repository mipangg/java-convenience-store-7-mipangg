package store.service;

import java.util.ArrayList;
import java.util.List;
import store.domain.Promotion;
import store.exception.ErrorCode;

public class PromotionManager {

    private final List<Promotion> promotions = new ArrayList<>();

    public void save(Promotion promotion) {
        promotions.add(promotion);
    }

    public Promotion findByName(String name) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(name)) {
                return promotion;
            }
        }
        throw new IllegalArgumentException(ErrorCode.PROMOTION_NOT_FOUND.getMessage());
    }

    public List<Promotion> findAll() {
        return new ArrayList<>(promotions);
    }

}
