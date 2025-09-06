package store.domain;

import store.exception.ErrorCode;

public class OrderItem {

    private final Product product;
    private int quantity;
    private int freeQuantity = 0;

    public OrderItem(Product product, int quantity) {
        validate(product, quantity);
        this.product = product;
        this.quantity = quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setFreeQuantity() {
        if (product.isActivePromotion()) {
            this.freeQuantity = product.getTotalFreeQuantity(quantity);
        }
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }

    public int getTotalPrice() {
        return product.getPrice() * quantity;
    }

    public int getTotalPromotionDiscount() {
        return product.getPrice() * freeQuantity;
    }

    public void addOneMoreQuantity() {
        this.quantity++;
    }

    private void validate(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException(ErrorCode.INVALID_ORDERITEM.getMessage());
        }
    }
}
