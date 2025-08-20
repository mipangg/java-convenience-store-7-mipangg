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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public void calculateTotalAmount() {
        this.totalAmount = 0;
        for (OrderItem orderItem : orderItems) {
            this.totalAmount += orderItem.getTotalAmount();
        }
    }
}
