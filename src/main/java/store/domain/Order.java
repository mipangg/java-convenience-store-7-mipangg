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

    public void setMembershipDisCountAmount() {
        this.membershipDisCountAmount =
                Math.min(8000, (int) ((totalAmount - promotionDisCountAmount) * 0.3));
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
