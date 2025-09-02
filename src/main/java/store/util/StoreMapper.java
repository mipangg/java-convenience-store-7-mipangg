package store.util;

import java.util.Map;
import store.domain.Promotion;

public class StoreMapper {

    private final StoreFormatter storeFormatter = new StoreFormatter();

    public Promotion toPromotion(Map<String, String> promotionInfo) {
        return new Promotion(
                promotionInfo.get("name"),
                storeFormatter.StringToInt(promotionInfo.get("buy")),
                storeFormatter.StringToInt(promotionInfo.get("get")),
                storeFormatter.StringToLocalDate(promotionInfo.get("start_date")),
                storeFormatter.StringToLocalDate(promotionInfo.get("end_date"))
        );
    }

}
