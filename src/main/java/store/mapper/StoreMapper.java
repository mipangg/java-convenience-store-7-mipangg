package store.mapper;

import java.util.Map;
import store.domain.Product;
import store.domain.Promotion;
import store.util.StoreFormatter;

public class StoreMapper {

    private final StoreFormatter storeFormatter = new StoreFormatter();

    public Promotion toPromotion(Map<String, String> map) {
        return new Promotion(
                map.get("name"),
                storeFormatter.strToInt(map.get("buy")),
                storeFormatter.strToInt(map.get("get")),
                storeFormatter.strToLocalDate(map.get("start_date")),
                storeFormatter.strToLocalDate(map.get("end_date"))
        );
    }

    public Product toProduct(Map<String, String> map, Promotion promotion) {
        return new Product(
                map.get("name"),
                storeFormatter.strToInt(map.get("price")),
                storeFormatter.strToInt(map.get("quantity")),
                promotion
        );
    }
}
