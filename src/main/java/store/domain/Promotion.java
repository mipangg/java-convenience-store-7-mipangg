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

    public String getName() {
        return name;
    }

    public int getGet() {
        return get;
    }

    // 프로모션 할인 적용을 위해 추가적으로 필요한 수량 반환
    public int getQuantityForPromotion(int amount) {
        return amount % (buy + get);
    }

    public boolean isActive() {
        LocalDate now = LocalDate.now();
        return startDate.isBefore(now) && endDate.isAfter(now);
    }

    private void validate(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        if (name == null || name.isEmpty() || buy < 0 || get < 0
                || startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalStateException(ErrorCode.INVALID_PROMOTION.getMessage());
        }
    }
}
