package store.model;

public class Product {
    private String name;
    private int price;
    private int stock;
    private String promotion;

    public Product(String name, String price, String stock, String promotion) {
        this.name = name;
        this.price = Integer.parseInt(price);
        this.stock = Integer.parseInt(stock);
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getPromotion() {
        return promotion;
    }

    public boolean isAvailable(int quantity) {
        return stock >= quantity;
    }

    public void reduceStock(int quantity) {
        stock -= quantity;
    }

    public int getTotalPrice(int quantity) {
        return price * quantity;
    }

    @Override
    public String toString() {
        return name + " " + price + " " + stock + " " + promotion;
    }
}
