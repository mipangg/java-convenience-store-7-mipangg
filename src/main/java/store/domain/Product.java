package store.domain;

public class Product {

    private String name;
    private int price;
    private int stock;
    private int promotionStock;
    private Promotion promotion;

    public Product(String name, int price, int stock, int promotionStock, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotionStock = promotionStock;
        this.promotion = promotion;
    }

}
