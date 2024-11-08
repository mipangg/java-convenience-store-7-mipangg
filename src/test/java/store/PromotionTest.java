package store;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PromotionTest {
    Promotion promotion;

    @BeforeEach
    void setUp() {
        promotion = new Promotion("탄산2+1","2","1","2024-01-01","2024-12-31");
    }

    @Test
    @DisplayName("오늘 날짜가 프로모션 기간에 포함되는지 반환한다.")
    void returnTodayIsOnPromotion() {
        assertThat(promotion.isOnPromotion()).isEqualTo(true);
    }
}
