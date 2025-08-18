package store.repository;

import java.util.ArrayList;
import java.util.List;
import store.domain.Promotion;

public class PromotionRepository {

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
        return null;
    }

    public List<Promotion> findAll() {
        return new ArrayList<>(promotions);
    }

}
