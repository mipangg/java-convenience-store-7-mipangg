package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.TestUtil;
import store.exception.ErrorCode;

class PromotionTests {

    @Test
    @DisplayName("프로모션 이름, 구매해야할 상품 개수, 무료 증정 상품 개수, 프로모션 시작 기간, 종료 기간을 저장할 수 있다")
    void createPromotionTest() {

        String name = "탄산2+1";
        int buy = 2;
        int get = 1;
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);

        Promotion promotion = new Promotion(name, buy, get, startDate, endDate);

        assertThat(promotion).isNotNull();
        assertThat(promotion.getName()).isEqualTo(name);

    }

    @Test
    @DisplayName("빈 이름, 0 보다 작은 개수, 빈 시작 기간이나 종료 기간, 시작 기간보다 앞선 종료 기간의 정보로 생성을 시도하면 INVALID_PROMOTION 예외가 발생한다")
    void createPromotionFailTest() {

        String name = "";
        int buy = 2;
        int get = 1;
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);

        assertThatThrownBy(
                () -> {
                    new Promotion(name, buy, get, startDate, endDate);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_PROMOTION.getMessage());

    }

    @Test
    @DisplayName("프로모션 활성 상태를 반환할 수 있다")
    void isActiveTest() {

        Promotion activePromotion = TestUtil.genPromotion();
        assertThat(activePromotion.isActive()).isTrue();

    }

    @Test
    @DisplayName("구매하려는 상품 개수가 프로모션 조건과 부합하는지 반환할 수 있다")
    void isEligiblePromotion() {

        Promotion promotion = new Promotion(
                "탄산2+1",
                2,
                1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31)
        );

        int eligibleQuantity = 3;
        int inEligibleQuantity = 4;

        assertThat(promotion.isEligible(eligibleQuantity)).isTrue();
        assertThat(promotion.isEligible(inEligibleQuantity)).isFalse();

    }

    @Test
    @DisplayName("상품별 총 구매 수량에서 무료 증정되는 상품 개수를 반환할 수 있다")
    void getTotalFreeQuantityTest() {

        Promotion promotion = new Promotion(
                "탄산2+1",
                2,
                1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31)
        );

        int quantity = 6;
        int freeQuantity = 2;

        assertThat(promotion.calculateTotalFreeQuantity(quantity)).isEqualTo(freeQuantity);

    }

}