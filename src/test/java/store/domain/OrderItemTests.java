package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.TestUtil;
import store.exception.ErrorCode;

class OrderItemTests {

    @Test
    @DisplayName("상품과 구매 수량을 저장하고 반환할 수 있다")
    void createOrderItemTest() {

        Product product = TestUtil.genPromotionProduct();
        int quantity = 6;
        OrderItem orderItem = new OrderItem(product, quantity);

        assertThat(orderItem).isNotNull();
        assertThat(orderItem.getProduct()).isEqualTo(product);
        assertThat(orderItem.getQuantity()).isEqualTo(quantity);
    }

    @Test
    @DisplayName("빈 상품이나 0보다 작거나 같은 수량으로 생성을 시도하면 INVALID_ORDERITEM 예외가 발생한다")
    void createOrderItemFailTest() {

        assertThatThrownBy(
                () -> {
                    new OrderItem(null, 6);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_ORDERITEM.getMessage());

    }

    @Test
    @DisplayName("상품별 총 가격을 반환할 수 있다")
    void getTotalPriceTest() {

        Product product = TestUtil.genPromotionProduct();
        int quantity = 6;
        OrderItem orderItem = new OrderItem(product, quantity);

        assertThat(orderItem.getTotalPrice()).isEqualTo(product.getPrice() * quantity);

    }


    @Test
    @DisplayName("상품별 총 프로모션 할인 가격을 반환할 수 있다")
    void getTotalPromotionDiscountTest() {

        OrderItem orderItem = TestUtil.genOrderItem();
        int expectedDiscount = orderItem.getFreeQuantity() * orderItem.getProduct().getPrice();

        assertThat(orderItem.getTotalPromotionDiscount()).isEqualTo(expectedDiscount);

    }


    @Test
    @DisplayName("주문 상품이 프로모션 상품이라면, 상품별 무료 증정 개수를 저장하고 반환할 수 있다")
    void setFreeQuantityTest() {

        OrderItem orderItem = TestUtil.genOrderItem();
        int expectedFreeQuantity = 2;

        orderItem.setFreeQuantity();
        assertThat(orderItem.getFreeQuantity()).isEqualTo(expectedFreeQuantity);
    
    }

    @Test
    @DisplayName("주문 수량을 1개 추가할 수 있다")
    void addOneMoreQuantityTest() {

        OrderItem orderItem = TestUtil.genOrderItem();
        int oldQuantity = orderItem.getQuantity();

        orderItem.addOneMoreQuantity();

        assertThat(orderItem.getQuantity()).isEqualTo(oldQuantity + 1);

    }
}