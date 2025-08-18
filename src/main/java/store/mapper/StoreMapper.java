package store.mapper;

import java.util.Map;
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
}
