package store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.InputModel;
import store.model.Order;
import store.model.OrderItem;

public class OrderTest {
    Order order = new Order();
    InputModel inputModel = new InputModel();
    List<OrderItem> orderItems;

    @BeforeEach
    void setUp() {
        order.setOrderItems(inputModel.getUserOrders("[콜라-3],[에너지바-5]"));
        orderItems = order.getOrderItems();
    }

    @Test
    @DisplayName("유저가 주문한 항목 리스트를 저장한다.")
    void storeUserOrderItems() {
        assertThat(orderItems).isNotNull();

        assertThat(orderItems.get(0).getProduct().getName()).isEqualTo("콜라");
        assertThat(orderItems.get(0).getQuantity()).isEqualTo(3);
        assertThat(orderItems.get(1).getProduct().getName()).isEqualTo("에너지바");
        assertThat(orderItems.get(1).getQuantity()).isEqualTo(5);
    }

    @Test
    @DisplayName("상품별 가격과 수량을 곱하여 총 가격을 구한다.")
    void calculateItemTotalPrice() {
        assertThat(orderItems.get(0).getTotalPrice()).isEqualTo(3000);
        assertThat(orderItems.get(1).getTotalPrice()).isEqualTo(10000);
    }
}
