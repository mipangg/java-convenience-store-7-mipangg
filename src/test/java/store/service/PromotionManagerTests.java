package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.TestUtil;
import store.domain.Promotion;
import store.exception.ErrorCode;

class PromotionManagerTests {

    private final PromotionManager promotionManager = new PromotionManager();

    @Test
    @DisplayName("프로모션 정보를 저장하고 조회할 수 있다")
    void savePromotionTest() {

        Promotion promotion = TestUtil.genPromotion();
        promotionManager.save(promotion);

        assertThat(promotionManager.getByName(promotion.getName())).isEqualTo(promotion);

    }

    @Test
    @DisplayName("이름으로 조회되는 프로모션이 없는 경우 PROMOTION_NOT_FOUND 예외가 발생한다")
    void getByNameFailTest() {

        String targetName = "탄산2+1";

        assertThatThrownBy(
                () -> {
                    promotionManager.getByName(targetName);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.PROMOTION_NOT_FOUND.getMessage());

    }

}