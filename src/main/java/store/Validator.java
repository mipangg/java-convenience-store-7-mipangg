package store;

import store.model.Inventory;
import store.model.OrderItem;
import store.model.Product;
import store.model.PromotionDiscount;

public class Validator {
    private final String YES = "Y";
    private final String NO = "N";
    private final boolean GO = true;
    private final boolean STOP = false;

    public Product validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException(Error.NOT_EXIST.getMessage());
        }
        return product;
    }

    public int validateQuantity(String quantityText) {
        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(Error.INCORRECT_FORMAT.getMessage());
        }
        return quantity;
    }

    public void validateStock(OrderItem orderItem, int stock) {
        if (orderItem.getQuantity() > stock) {
            throw new IllegalArgumentException(Error.OUT_OF_STOCK.getMessage());
        }
    }

    public boolean validateYesOrNo(String userInput) {
        userInput = userInput.replace(" ", "");
        if (userInput.equals(YES)) { return GO; }
        if (userInput.equals(NO)) { return STOP; }
        throw new IllegalArgumentException(Error.INCORRECT_INPUT.getMessage());
    }
}
