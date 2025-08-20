package store.domain;

import java.util.List;

public class Order {
    private List<OrderItem> orderItems;
    private int totalAmount;
    private int promotionDiscount;
    private int membershipDiscount;

    public Order(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
