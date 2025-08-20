package store.domain;

import java.time.LocalDate;
import store.exception.ErrorCode;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        validate(name, buy, get, startDate, endDate);
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        if (name == null || name.isEmpty() || buy < 0 || get < 0
        || startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PROMOTION.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public int getAmountForPromotion(int amount) {
        return amount % (this.buy + this.get);
    }

    public boolean isActive() {
        LocalDate now = LocalDate.now();
        return startDate.isBefore(now) && endDate.isAfter(now);
    }
}
