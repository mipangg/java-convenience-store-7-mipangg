package store;

import java.time.LocalDate;
import store.domain.Product;
import store.domain.Promotion;

public class TestUtil {

    public static Promotion genPromotion() {
        return new Promotion(
                "탄산2+1",
                2,
                1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31)
        );
    }

    public static Product genPromotionProduct() {
        return new Product(
                "콜라",
                1000,
                10,
                new Promotion(
                        "탄산2+1",
                        2,
                        1,
                        LocalDate.of(2025, 1, 1),
                        LocalDate.of(2025, 12, 31)
                )
        );
    }

}
