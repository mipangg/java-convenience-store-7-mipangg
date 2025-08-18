package store.domain;

import store.exception.ErrorCode;

public class Item {
    private final String name;
    private final int price;
    private int stock;
    private final Promotion promotion;

    public Item(String name, int price, int stock, Promotion promotion) {
        // TODO: validate 구현
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

    public Promotion getPromotion() {
        return promotion;
    }

    public boolean isActivePromotion() {
        if (promotion == null) {
            return false;
        }
        return promotion.isActive();
    }

    // 차감 후 업데이트 된 재고 반환
    public int updateStock(int soldAmount) {
        if (this.stock < soldAmount) {
            throw new IllegalArgumentException(ErrorCode.EXCEED_STOCK.getMessage());
        }
        this.stock -= soldAmount;
        return this.stock;
    }
}
