package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final List<OrderItem> orderItems;
    private final List<OrderItem> orderPromotionItems = new ArrayList<>();
    private int totalQuantity = 0;
    private int totalAmount = 0;
    private int membershipDisCountAmount = 0;
    private int promotionDisCountAmount = 0;

    public Order(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        setOrderPromotionItems();
        setTotalQuantityAndTotalAmount();
        setMembershipDisCountAmount();
        setPromotionDisCountAmount();
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public List<OrderItem> getOrderPromotionItems() {
        return orderPromotionItems;
    }

    public int getTotalQuantity() {
        return totalQuantity;
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

    private void setOrderPromotionItems() {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().isActivePromotion()) {
                orderPromotionItems.add(orderItem);
            }
        }
    }

    private void setTotalQuantityAndTotalAmount() {
        for (OrderItem orderItem : orderItems) {
            totalQuantity += orderItem.getQuantity();
            totalAmount += orderItem.getTotalPrice();
        }
    }

    private void setMembershipDisCountAmount() {
        for (OrderItem orderItem : orderItems) {
            if (!orderItem.getProduct().isActivePromotion()) {
                membershipDisCountAmount += orderItem.getTotalPrice();
            }
        }
        membershipDisCountAmount = Math.min(8000, (int) (membershipDisCountAmount * 0.3));
    }

    private void setPromotionDisCountAmount() {
        for (OrderItem promotionItem : orderPromotionItems) {
            promotionDisCountAmount +=
                    (promotionItem.getProduct().getPromotionQuantity(promotionItem.getQuantity())
                            * promotionItem.getProduct().getPrice());
        }
    }
}
