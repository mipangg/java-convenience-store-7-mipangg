package store;

import java.time.LocalDate;
import java.util.List;
import camp.nextstep.edu.missionutils.DateTimes;

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

    public boolean isOnPromotion() {
        String date = getTodayDate();
        List<String> dates = List.of(startDate, date, endDate);
        List<String> sortedDates = dates.stream()
                .map(LocalDate::parse)
                .sorted()
                .map(LocalDate::toString)
                .toList();
        return sortedDates.get(1).equals(date);
    }

    public String getTodayDate() {
        String DATE_DELIMITER = "T";
        String today = LocalDate.now().toString();
        return today.split(DATE_DELIMITER)[0];
    }
}
