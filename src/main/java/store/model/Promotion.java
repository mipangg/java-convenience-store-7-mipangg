package store.model;

import java.time.LocalDate;
import java.util.List;
import camp.nextstep.edu.missionutils.DateTimes;

public class Promotion {
    private String promotionType;
    private int buy;
    private int get;
    private String startDate;
    private String endDate;

    public Promotion(String name, String buy, String get, String startDate, String endDate) {
        this.promotionType = name;
        this.buy = Integer.parseInt(buy);
        this.get = Integer.parseInt(get);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public int getPromotionDiscount(int quantity, int price) {
        return (quantity / (buy + get)) * (((buy + get) * price) - (get * price));
    }

    public boolean isDivisible(int quantity) {
        return quantity % (buy + get) == 0;
    }

    public boolean isAvailable() {
        String today = getToday();
        List<String> dates = List.of(startDate, today, endDate);
        List<String> sortedDates = dates.stream()
                .map(LocalDate::parse)
                .sorted()
                .map(LocalDate::toString)
                .toList();
        return sortedDates.get(1).equals(today);
    }

    private String getToday() {
        return DateTimes.now().toString().split("T")[0];
    }
}
