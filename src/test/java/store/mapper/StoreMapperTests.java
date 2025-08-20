package store.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;

class StoreMapperTests {

    private final StoreMapper storeMapper = new StoreMapper();

    @Test
    @DisplayName("Map을 인자로 받아 Promotion을 반환할 수 있다")
    void toPromotionTest() {

        Map<String, String> promotionInfo = Map.of(
                "name", "탄산2+1",
                "buy", "2",
                "get", "1",
                "start_date", "2025-01-01",
                "end_date", "2025-12-31"
        );

        Promotion expectedPromotion = new Promotion(
                "탄산2+1",
                2,
                1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31)
        );

        Promotion actualPromotion = storeMapper.toPromotion(promotionInfo);

        assertThat(expectedPromotion.getName()).isEqualTo(actualPromotion.getName());

    }

    @Test
    @DisplayName("Map을 인자로 받아 Product을 반환할 수 있다")
    void toProductTest() {

        Map<String, String> ProductInfo = Map.of(
                "name", "콜라",
                "price", "1000",
                "quantity", "10",
                "promotion", "탄산2+1"
        );

        Promotion promotion = new Promotion(
                "탄산2+1",
                2,
                1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31)
        );

        Product expectedProduct = new Product("콜라", 1000, 10, promotion);

        Product actualProduct = storeMapper.toProduct(ProductInfo, promotion);

        assertThat(expectedProduct.getName()).isEqualTo(actualProduct.getName());
        assertThat(expectedProduct.getPrice()).isEqualTo(actualProduct.getPrice());
    }

}