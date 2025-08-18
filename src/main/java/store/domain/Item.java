package store.domain;

import java.util.Map;
import store.exception.ErrorCode;

public class Item {

    String name;
    int price;
    int stock;
    String promotion;

    public Item(Map<String, String> productInfo) {
        validate(productInfo);
        this.name = productInfo.get("name");
        this.price = strToInt(productInfo.get("price"));
        this.stock = strToInt(productInfo.get("quantity"));
        this.promotion = productInfo.get("promotion");
    }

    private void validate(Map<String, String> productInfo) {
        if (
                productInfo.get("name") == null
                || productInfo.get("price") == null
                || productInfo.get("quantity") == null
                || productInfo.get("promotion") == null
        ) {
            throw new IllegalArgumentException(ErrorCode.INVALID_ITEM.getMessage());
        }
    }

    private int strToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorCode.INVALID_ITEM.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public String getPromotion() {
        return promotion;
    }

    // 차감 후 재고 수량을 반환
    public int updateStock(int soldAmount) {
        if (this.stock < soldAmount) {
            throw new IllegalArgumentException(ErrorCode.EXCEED_STOCK.getMessage());
        }
        this.stock -= soldAmount;
        return this.stock;
    }
}
