package store.dto;

public class OrderItemResponse {
    private final String name;
    private int quantity;
    private int price;

    public OrderItemResponse(String name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public void mergeOrderItem(int quantity, int price) {
        this.quantity += quantity;
        this.price += price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
