package store.dto;

import store.exception.ErrorCode;

public record OrderItemRequest(
        String name,
        int quantity
) {
    public static OrderItemRequest toOrderItemRequest(String[] info) {
        try {
            return new OrderItemRequest(info[0], Integer.parseInt(info[1]));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(ErrorCode.INVALID_INPUT.getMessage());
        }
    }
}
