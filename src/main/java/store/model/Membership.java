package store.model;

import store.model.OrderItem;

public class Membership {
    private final int MINIMUM_PRICE = 8000;

    public boolean isAvailableMembership(OrderItem orderItem) {
        int totalPrice = getTotalPrice(orderItem);
        return totalPrice >= MINIMUM_PRICE;
    }

    public int getMembershipDiscount(OrderItem orderItem) {
       int totalPrice = getTotalPrice(orderItem);
       return (int) (totalPrice * 0.3);
    }

    private int getTotalPrice(OrderItem orderItem) {
        return orderItem.getProduct().getTotalPrice(orderItem.getQuantity());
    }
}
