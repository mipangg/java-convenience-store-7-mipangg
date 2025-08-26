package store.domain;

public class OrderItem {

    private final Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int addQuantity(int quantity) {
        this.quantity += quantity;
        return this.quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return product.getPrice() * quantity;
    }

    public String toString() {
        return String.format("%s\t%d\t%,d", product.getName(), quantity, getTotalPrice());
    }
}
