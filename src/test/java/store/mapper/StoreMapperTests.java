package store.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        assertThat(expectedPromotion.getBuy()).isEqualTo(actualPromotion.getBuy());
        assertThat(expectedPromotion.getGet()).isEqualTo(actualPromotion.getGet());

    }
}