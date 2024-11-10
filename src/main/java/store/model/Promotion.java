package store.model;

import java.time.LocalDate;
import java.util.List;
import camp.nextstep.edu.missionutils.DateTimes;

public class Promotion {
    private final String promotionType;
    private final int buy;
    private final int get;
    private final String startDate;
    private final String endDate;

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

    // 프로모션 적용 시 할인되는 금액 반환
    public int getPromotionDiscount(int totalPrice) {
        return (int) (totalPrice / (buy + get));
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
