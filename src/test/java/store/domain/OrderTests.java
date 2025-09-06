package store.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.TestUtil;
import store.exception.ErrorCode;

class OrderTests {

    @Test
    @DisplayName("주문 아이템 목록을 저장하고 반환할 수 있다")
    void createOrderTest() {

        List<OrderItem> orderItems = TestUtil.genOrderItems();

        Order order = new Order(orderItems);

        assertThat(order).isNotNull();
        assertThat(order.getOrderItems().get(0).getProduct().getName())
                .isEqualTo("콜라");
        assertThat(order.getOrderItems().get(0).getQuantity())
                .isEqualTo(10);
        assertThat(order.getOrderItems().get(1).getProduct().getName())
                .isEqualTo("사이다");
        assertThat(order.getOrderItems().get(1).getQuantity())
                .isEqualTo(3);

    }

    @Test
    @DisplayName("빈 주문 상품 리스트로 생성을 시도하면 INVALID_ORDER 예외가 발생한다")
    void createOrderFailTest() {

        assertThatThrownBy(
                () -> {
                    new Order(null);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_ORDER.getMessage());

    }

    @Test
    @DisplayName("총 구매액을 저장하고 반환할 수 있다")
    void calculateTotalPriceTest() {

        Order order = TestUtil.genOrder();
        int expectedTotalPrice = 23000;

        order.calculateTotalPrice();
        assertThat(order.getTotalPrice()).isEqualTo(expectedTotalPrice);

    }

    @Test
    @DisplayName("프로모션 할인 상품 정보(이름, 개수)를 저장하고 반환할 수 있다")
    void setPromotionOrderItemsTest()  {

        Order order = TestUtil.genOrder();
        order.setPromotionOrderItems();
        assertThat(order.getPromotionOrderItems()).containsAll(order.getPromotionOrderItems());

    }

    @Test
    @DisplayName("프로모션 할인 금액을 저장하고 반환할 수 있다")
    void calculateTotalPromotionDiscountTest() {

        Order order = TestUtil.genOrder();
        order.getOrderItems().forEach(OrderItem::setFreeQuantity);
        order.setPromotionOrderItems();
        order.calculateTotalPromotionDiscount();

        int expectedTotalPromotionDiscount = 4000;

        assertThat(order.getTotalPromotionDiscount()).isEqualTo(expectedTotalPromotionDiscount);

    }

    @Test
    @DisplayName("멤버십 할인 금액을 저장하고 반환할 수 있다")
    void calculateTotalMembershipDiscountTest() {

        Order order = TestUtil.genOrder();
        order.calculateTotalMembershipDiscount();

        int expectedTotalMembershipDiscount = 3000;

        assertThat(order.getTotalMembershipDiscount()).isEqualTo(expectedTotalMembershipDiscount);
    }

    @Test
    @DisplayName("할인을 적용한 최종 금액을 반환할 수 있다")
    void getTotalPriceWithDiscountTest() {

        Order order = TestUtil.genOrder();
        order.calculateTotalPrice();
        order.getOrderItems().forEach(OrderItem::setFreeQuantity);
        order.setPromotionOrderItems();
        order.calculateTotalPromotionDiscount();
        order.calculateTotalMembershipDiscount();

        int expectedTotalPrice = 16000;

        assertThat(order.getTotalPriceWithDiscount()).isEqualTo(expectedTotalPrice);

    }

    @Test
    @DisplayName("주문 상품 리스트에 새로운 주문 상품을 추가할 수 있다")
    void addOrderItemTest() {

        Order order = TestUtil.genOrder();
        OrderItem newOrderItem = new OrderItem(
                new Product("물",500,10,null),
                3
        );

        assertThat(order.getOrderItems()).doesNotContain(newOrderItem);

        order.addOrderItem(newOrderItem);

        assertThat(order.getOrderItems()).contains(newOrderItem);

    }
}