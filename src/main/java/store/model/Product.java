package store.model;

import store.Error;

public class Product {
    private final String name;
    private final int price;
    private int stock;
    private final String promotion;

    public Product(String name, String price, String stock, String promotion) {
        this.name = name;
        this.price = Integer.parseInt(price);
        this.stock = Integer.parseInt(stock);
        this.promotion = promotion;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() { return name; }

    public int getPrice() { return price; }

    public int getStock() { return stock; }

    public String getPromotion() { return promotion; }

    public boolean isAvailable(int quantity) { return stock >= quantity; }

    public void reduceStock(int quantity) {
        stock -= quantity;
        if (stock < 0) {
            throw new IllegalArgumentException(Error.OUT_OF_STOCK.getMessage());
        }
    }

    public int getTotalPrice(int quantity) {
        return price * quantity;
    }

    @Override
    public String toString() {
        String text = "- " + name + " " + String.format("%,d", price) + "원 ";
        text += toStringStock();
        text += toStringPromotion();
        return text;
    }

    private String toStringStock() {
        if (stock == 0) {
            return "재고 없음 ";
        }
        return Integer.toString(stock) + "개 ";
    }

    private String toStringPromotion() {
        if (promotion.equals("null")) {
            return "";
        }
        return promotion;
    }
}
