package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.TestUtil;
import store.domain.Product;
import store.domain.Promotion;

class StoreMapperTests {

    private final StoreMapper storeMapper = new StoreMapper();

    @Test
    @DisplayName("Map을 Promotion으로 변환하여 반환할 수 있다")
    void toPromotionTest() {

        Map<String, String> promotionInfo = Map.of(
                "name", "탄산2+1",
                "buy", "2",
                "get", "1",
                "start_date", "2025-01-01",
                "end_date", "2025-12-31"
        );
        Promotion expectedPromotion = TestUtil.genPromotion();

        Promotion actualPromotion = storeMapper.toPromotion(promotionInfo);

        assertThat(actualPromotion.getName()).isEqualTo(expectedPromotion.getName());
        assertThat(actualPromotion.isActive()).isEqualTo(expectedPromotion.isActive());

    }

    @Test
    @DisplayName("Map을 Product로 변환하여 반환할 수 있다")
    void toProductTest() {

        Promotion promotion = TestUtil.genPromotion();
        Map<String, String> productInfo = Map.of(
                "name", "콜라",
                "price", "1000",
                "quantity", "10",
                "promotion", "탄산2+1"
        );

        Product product = storeMapper.toProduct(productInfo, promotion);

        assertThat(product.getName()).isEqualTo(productInfo.get("name"));
        assertThat(product.getPrice()).isEqualTo(Integer.parseInt(productInfo.get("price")));

    }

}