package store.model;

public class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void setProduct(Product product) { this.product = product; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Product getProduct() { return product; }

    public int getQuantity() { return quantity; }

    public String getProductName() { return product.getName(); }

    public int getTotalPrice() { return product.getPrice() * quantity; }
}
