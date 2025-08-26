package store.domain;

import store.exception.ErrorCode;

public class Product {

    private String name;
    private int price;
    private int stock;
    private Promotion promotion;

    public Product(String name, int price, int stock, Promotion promotion) {
        validate(name, price, stock);
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotion = promotion;
    }

    public void updateStock(int amount) {
        stock -= amount;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public boolean isAvailable(int amount) {
        return stock >= amount;
    }

    private void validate(String name, int price, int stock) {
        if (name == null || name.isEmpty() || price < 0 || stock < 0) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PRODUCT.getMessage());
        }
    }
}
