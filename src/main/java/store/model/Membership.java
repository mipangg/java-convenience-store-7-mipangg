package store.model;

import store.model.OrderItem;

public class Membership {
    private final int MINIMUM_PRICE = 8000;

    public boolean isAvailableMembership(OrderItem orderItem) {
        return orderItem.getTotalPrice() >= MINIMUM_PRICE;
    }

    public int getMembershipDiscount(OrderItem orderItem) {
       return (int) (orderItem.getTotalPrice() * 0.3);
    }
}
