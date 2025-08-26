package store.domain;

import java.util.List;

public class Order {

    private final List<OrderItem> orderItems;
    private int totalAmount;
    private int membershipDisCountAmount;
    private int promotionDisCountAmount;

    public Order(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setTotalAmount() {
        int totalAmount = 0;
        for (OrderItem orderItem : orderItems) {
            totalAmount += orderItem.getTotalPrice();
        }
        this.totalAmount = totalAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getMembershipDisCountAmount() {
        return membershipDisCountAmount;
    }

    public int getPromotionDisCountAmount() {
        return promotionDisCountAmount;
    }
}
