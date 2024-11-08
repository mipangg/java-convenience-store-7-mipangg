package store;

import java.util.List;

public class Promotion {
    private String promotionType;
    private int buy;
    private int get;
    private String startDate;
    private String endDate;

    public Promotion(String promotionType, String buy, String get, String startDate, String endDate) {
        this.promotionType = promotionType;
        this.buy = Integer.parseInt(buy);
        this.get = Integer.parseInt(get);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean isOnPromotion(String date) {
        List<String> dates = List.of(startDate, date, endDate);
        dates.sort(String::compareTo);
        return dates.get(1).equals(date);
    }
}
