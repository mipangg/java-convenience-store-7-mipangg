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

    public boolean isActivePromotion() {
        return promotion != null && promotion.isActive();
    }

    public boolean isAvailable(int quantity) {
        return stock >= quantity;
    }

    public void updateStock(int quantity) {
        this.stock -= quantity;
    }

    public int calculateShortageStock(int quantity) {
        return quantity - stock;
    }

    private void validate(String name, int price, int stock) {
        if (name == null || name.isEmpty() || price < 0 || stock < 0) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PRODUCT.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getTotalFreeQuantity(int quantity) {
        return promotion.calculateTotalFreeQuantity(quantity);
    }

    public String toString() {
        return String.format("- %s %,d원 %s개 %s", name, price, getStock(), getPromotion());
    }

    private String getStock() {
        if (stock == 0) {
            return "재고 없음";
        }
        return String.valueOf(stock);
    }

    private String getPromotion() {
        if (promotion == null) {
            return "";
        }
        return promotion.getName();
    }
}
