package store.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        ).isInstanceOf(IllegalStateException.class)
                .hasMessage(ErrorCode.INVALID_PROMOTION.getMessage());

    }
}