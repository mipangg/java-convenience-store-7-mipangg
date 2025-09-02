package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.TestUtil;
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

}