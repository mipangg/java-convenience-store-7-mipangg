package store.domain;

import store.exception.ErrorCode;

public class Product {
    private final String name;
    private final int price;
    private int stock;
    private final Promotion promotion;

    public Product(String name, int price, int stock, Promotion promotion) {
        validate(name, price, stock);
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void updateStock(int soldAmount) {
        if (soldAmount > stock) {
            throw new IllegalArgumentException(ErrorCode.EXCEED_STOCK.getMessage());
        }
        this.stock -= soldAmount;
    }

    public boolean isPromotion() {
        if (promotion == null) {
            return false;
        }
        return promotion.isActive();
    }

    private void validate(String name, int price, int stock) {
        if (name == null || name.isEmpty() || price < 0 || stock < 0) {
            throw new IllegalStateException(ErrorCode.INVALID_PRODUCT.getMessage());
        }
    }

}
