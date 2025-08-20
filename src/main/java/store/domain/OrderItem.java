package store.domain;

public class OrderItem {
    private final Item item;
    private final int quantity;

    public OrderItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return item.getTotalPrice(quantity);
    }

}
