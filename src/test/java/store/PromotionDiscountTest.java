package store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.model.InputModel;
import store.model.Order;
import store.model.OrderItem;
import store.model.PromotionDiscount;

public class PromotionDiscountTest {
    PromotionDiscount promotionDiscount = new PromotionDiscount();
    Order order = new Order();
    InputModel inputModel = new InputModel();
    List<OrderItem> orderItems;

    @BeforeEach
    void setUp() {
        order.setOrderItems(inputModel.getUserOrders("[콜라-3],[에너지바-5],[컵라면-2],[사이다-2],[오렌지주스-4]"));
        orderItems = order.getOrderItems();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0:true",
            "1:false",
            "2:true",
            "3:true",
            "4:true"
    }, delimiter = ':')
    @DisplayName("프로모션 제품인지 확인한다.")
    void checkIfItIsPromotion(int index, boolean expected) {
        assertThat(promotionDiscount.isPromotion(orderItems.get(index))).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0:true",
            "2:false",
            "3:true",
            "4:true"
    }, delimiter = ':')
    @DisplayName("구매하고 싶은 수량 만큼 프로모션 상품의 재고가 충분한지 확인한다.")
    void checkEnoughPromotionStock(int index, boolean expected) {
        assertThat(promotionDiscount.isEnoughStock(orderItems.get(index))).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0:true",
            "3:false",
            "4:true"
    }, delimiter = ':')
    @DisplayName("구매하려는 수량이 프로모션 적용 기준에 맞는 수량인지 확인한다.")
    void checkIsPromotionFormat(int index, boolean expected) {
        promotionDiscount.isPromotion(orderItems.get(index));
        assertThat(promotionDiscount.isPromotionFormat(orderItems.get(index))).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0:1000",
            "4:3600"
    }, delimiter = ':')
    @DisplayName("프로모션 할인 금액을 계산하고 프로모션 재고를 차감한다.")
    void calculatePromotionDiscountAndReduceStock(int index, int expected) {
        promotionDiscount.isPromotion(orderItems.get(index));
        assertThat(promotionDiscount.calculatePromotionDiscount(orderItems.get(index))).isEqualTo(expected);
    }
}
