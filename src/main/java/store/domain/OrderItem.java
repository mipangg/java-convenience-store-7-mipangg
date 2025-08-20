package store.domain;

import store.exception.ErrorCode;

public class OrderItem {
    private final Product product;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        validate(product, quantity);
        this.product = product;
        this.quantity = quantity;
    }

    public int getTotalAmount() {
        return product.getPrice() * quantity;
    }

    private void validate(Product product, int quantity) {
        if (product == null || quantity < 0) {
            throw new IllegalArgumentException(ErrorCode.INVALID_FORMAT.getMessage());
        }
    }

}
