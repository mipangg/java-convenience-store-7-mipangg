package store.dto;

public record OrderItemRequest(
        String name,
        int quantity
) {
    public static OrderItemRequest toOrderItemRequest(String[] info) {
        return new OrderItemRequest(info[0], Integer.parseInt(info[1]));
    }
}
