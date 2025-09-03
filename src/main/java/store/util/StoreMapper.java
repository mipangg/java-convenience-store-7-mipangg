package store.util;

import java.util.Map;
import store.domain.OrderItem;
import store.domain.Product;
import store.domain.Promotion;

public class StoreMapper {

    private final StoreFormatter storeFormatter = new StoreFormatter();

    public Promotion toPromotion(Map<String, String> promotionInfo) {
        return new Promotion(
                promotionInfo.get("name"),
                storeFormatter.stringToInt(promotionInfo.get("buy")),
                storeFormatter.stringToInt(promotionInfo.get("get")),
                storeFormatter.stringToLocalDate(promotionInfo.get("start_date")),
                storeFormatter.stringToLocalDate(promotionInfo.get("end_date"))
        );
    }

    public Product toProduct(Map<String, String> productInfo, Promotion promotion) {
        return new Product(
                productInfo.get("name"),
                storeFormatter.stringToInt(productInfo.get("price")),
                storeFormatter.stringToInt(productInfo.get("quantity")),
                promotion
        );
    }

    public OrderItem toOrderItem(Map<String, String> orderItemInfo, Product product) {
        return new OrderItem(
                product,
                storeFormatter.stringToInt(orderItemInfo.get("quantity"))
        );
    }

}
