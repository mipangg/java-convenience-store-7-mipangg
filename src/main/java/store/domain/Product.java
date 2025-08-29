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

    public void updateStock(int quantity) {
        if (quantity > stock) {
            throw new IllegalArgumentException(ErrorCode.EXCEED_STOCK.getMessage());
        }
        stock -= quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    // 프로모션 상품만 존재할 때 일반 상품을 "재고 없음" 상태로 반환
    public Product getNormalProduct() {
        return new Product(name, price, 0, null);
    }

    // 구매한 프로모션 상품 개수 반환
    public int getPromotionQuantity(int quantity) {
        return promotion.calculateTotalFreeGets(quantity);
    }

    public boolean isPromotion() {
        return promotion != null;
    }

    public boolean isActivePromotion() {
        return promotion != null && promotion.isActive();
    }

    public String toString() {
        return String.format("- %s %,d원 %s %s", name, price, getStrStock(), getPromotion());
    }

    private String getStrStock() {
        if (stock == 0) {
            return "재고 없음";
        }
        return stock + "개";
    }

    private String getPromotion() {
        if (promotion == null) {
            return "";
        }
        return promotion.getName();
    }

    private void validate(String name, int price, int stock) {
        if (name == null || name.isEmpty() || price < 0 || stock < 0) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PRODUCT.getMessage());
        }
    }
}
