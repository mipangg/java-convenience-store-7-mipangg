package store.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;
import store.exception.ErrorCode;

class StoreMapperTests {

    private final StoreMapper storeMapper = new StoreMapper();

    @Test
    @DisplayName("promotion 정보가 담긴 map을 Promotion 형태로 반환할 수 있다")
    void toPromotionTest() {

        Map<String, String> promotionMap = Map.of(
                "name", "탄산2+1",
                "buy", "2",
                "get", "1",
                "start_date", "2025-01-01",
                "end_date", "2025-12-31"
        );

        Promotion promotion = storeMapper.toPromotion(promotionMap);
        assertThat(promotion.getName()).isEqualTo(promotionMap.get("name"));
    }

    @Test
    @DisplayName("잘못된 형식으로 promotion을 생성할 수 없는 경우 예외가 발생한다")
    void toPromotionFailTest() {

        Map<String, String> promotionMap = Map.of(
                "name", "탄산2+1",
                "buy", "2",
                "get", "1",
                "start_date", "2025-12-31",
                "end_date", "2025-01-01"
        );

        assertThatThrownBy(
                () -> {
                    storeMapper.toPromotion(promotionMap);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_PROMOTION.getMessage());

    }

    @Test
    @DisplayName("product 정보가 담긴 map을 Product 형태로 반환할 수 있다")
    void toProductTest() {

        Map<String, String> productMap = Map.of(
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

        Product product = storeMapper.toProduct(productMap, promotion);
        assertThat(product.getName()).isEqualTo(productMap.get("name"));
        assertThat(product.getPrice()).isEqualTo(Integer.parseInt(productMap.get("price")));

    }

    @Test
    @DisplayName("잘못된 형식으로 product를 생성할 수 없는 경우 예외가 발생한다")
    void toProductFailTest() throws Exception {

        Map<String, String> productMap = Map.of(
                "name", "콜라",
                "price", "-1000",
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

        assertThatThrownBy(
                () -> {
                    storeMapper.toProduct(productMap, promotion);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_PRODUCT.getMessage());

    }
}