package store.domain;

import java.util.ArrayList;
import java.util.List;
import store.exception.ErrorCode;

public class Order {

    private final List<OrderItem> orderItems;
    private List<OrderItem> promotionOrderItems;
    private int totalPrice = 0;
    private int totalPromotionDiscount = 0;
    private int totalMembershipDiscount = 0;

    public Order(List<OrderItem> orderItems) {
        validate(orderItems);
        this.orderItems = orderItems;
    }

    public void calculateTotalPrice() {
        totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
    }

    public void setPromotionOrderItems() {
        promotionOrderItems = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().isActivePromotion()) {
                promotionOrderItems.add(orderItem);
            }
        }
    }

    public void calculateTotalPromotionDiscount() {
        totalPromotionDiscount = 0;
        for (OrderItem promotionOrderItem : promotionOrderItems) {
            totalPromotionDiscount += promotionOrderItem.getTotalPromotionDiscount();
        }
    }

    public void calculateTotalMembershipDiscount() {
        totalMembershipDiscount = 0;
        int totalPriceWithoutDiscount = 0;
        for (OrderItem orderItem : orderItems) {
            if (!orderItem.getProduct().isActivePromotion()) {
                totalPriceWithoutDiscount += orderItem.getTotalPrice();
            }
        }
        totalMembershipDiscount = Math.min(8000, (int) (totalPriceWithoutDiscount * 0.3));
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public List<OrderItem> getPromotionOrderItems() {
        return promotionOrderItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getTotalPromotionDiscount() {
        return totalPromotionDiscount;
    }

    public int getTotalMembershipDiscount() {
        return totalMembershipDiscount;
    }

    public int getTotalPriceWithDiscount() {
        return totalPrice - totalPromotionDiscount - totalMembershipDiscount;
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (OrderItem orderItem : orderItems) {
            totalQuantity += orderItem.getQuantity();
        }
        return totalQuantity;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    private void validate(List<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.INVALID_ORDER.getMessage());
        }
    }

}
