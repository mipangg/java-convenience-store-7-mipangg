package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
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

    public String getName() {
        return name;
    }

    public boolean isActive() {
        // ApplicationTest.java 기간에_해당하지_않는_프로모션_적용() 테스트 통과를 위한 라이브러리 활용
        LocalDate now = DateTimes.now().toLocalDate();
        return (now.isEqual(startDate) || startDate.isBefore(now))
                && (now.isEqual(endDate) ||  endDate.isAfter(now));
    }

    public boolean isEligible(int quantity) {
        return quantity % (buy + get) == 0;
    }

    public int calculateTotalFreeQuantity(int quantity) {
        return (quantity / (buy + get)) * get;
    }

    private void validate(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        if (name == null || name.isEmpty() || buy <= 0 || get <= 0
        || startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PROMOTION.getMessage());
        }
    }
}
