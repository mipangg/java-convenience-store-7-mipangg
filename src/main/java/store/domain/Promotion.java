package store.domain;

import java.time.LocalDate;
import store.exception.ErrorCode;

public class Promotion {

    private String name;
    private int buy;
    private int get;
    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        validate(name, buy, get, startDate, endDate);
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isActive() {
        LocalDate now = LocalDate.now();
        return startDate.isBefore(now) && endDate.isAfter(now);
    }

    public boolean matchCondition(int quantity) {
        return quantity % (buy + get) == 0;
    }

    public String getName() {
        return name;
    }

    // 프로모션 상품 개수 반환
    public int calculateTotalFreeGets(int quantity) {
        return quantity / (buy + get);
    }

    private void validate(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        if (name == null || name.isEmpty() || buy <= 0 || get <= 0
                || startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PROMOTION.getMessage());
        }
    }

}
