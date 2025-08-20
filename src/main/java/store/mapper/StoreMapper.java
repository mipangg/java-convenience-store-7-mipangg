package store.mapper;

import java.util.Map;
import store.domain.Item;
import store.domain.OrderItem;
import store.domain.Promotion;
import store.util.StoreFormatter;

public class StoreMapper {

    private final StoreFormatter storeFormatter = new StoreFormatter();

    public Promotion toPromotion(Map<String, String> promotionInfo) {
        return new Promotion(
                promotionInfo.get("name"),
                storeFormatter.strToInt(promotionInfo.get("buy")),
                storeFormatter.strToInt(promotionInfo.get("get")),
                storeFormatter.strToLocalDate(promotionInfo.get("start_date")),
                storeFormatter.strToLocalDate(promotionInfo.get("end_date"))
        );
    }

    public Item toItem(Map<String, String> itemInfo, Promotion promotion) {
        return new Item(
                itemInfo.get("name"),
                storeFormatter.strToInt(itemInfo.get("price")),
                storeFormatter.strToInt(itemInfo.get("quantity")),
                promotion
        );
    }

    public OrderItem toOrderItem(Item item, int quantity) {
        return new OrderItem(item, quantity);
    }
}
