package store.util;

import java.util.Map;
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

}
