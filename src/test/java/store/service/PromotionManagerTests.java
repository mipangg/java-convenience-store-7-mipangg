package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;
import store.exception.ErrorCode;

class PromotionManagerTests {

    private final PromotionManager promotionManager = new PromotionManager();

    @Test
    @DisplayName("프로모션을 저장할 수 있다")
    void saveTest() {

        Promotion promotion = new Promotion(
                "탄산2+1",
                2,
                1,
                LocalDate.of(2025,1,1),
                LocalDate.of(2025, 12, 31)
        );

        List<Promotion> oldPromotions = promotionManager.findAll();

        promotionManager.save(promotion);

        List<Promotion> newPromotions = promotionManager.findAll();

        assertThat(newPromotions).hasSize(oldPromotions.size() + 1);
        assertThat(newPromotions.getLast().getName()).isEqualTo(promotion.getName());

    }

    @Test
    @DisplayName("프로모션 이름으로 프로모션 정보를 조회할 수 있다")
    void findByNameTest() {

        String targetName = "탄산2+1";

        Promotion promotion = new Promotion(
                "탄산2+1",
                2,
                1,
                LocalDate.of(2025,1,1),
                LocalDate.of(2025, 12, 31)
        );

        promotionManager.save(promotion);

        Promotion findPromotion = promotionManager.findByName(targetName);

        assertThat(findPromotion).isNotNull();
        assertThat(findPromotion.getName()).isEqualTo(targetName);

    }

    @Test
    @DisplayName("찾을 수 없는 프로모션 조회를 시도할 경우 PROMOTION_NOT_FOUND 예외가 발생한다")
    void findByNameFailTest() {

        String targetName = "탄산2+1";

        assertThatThrownBy(
                () -> {
                    promotionManager.findByName(targetName);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.PROMOTION_NOT_FOUND.getMessage());
    }
}