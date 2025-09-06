package store.exception;

public enum ErrorCode {
    INVALID_FORMAT("[ERROR] 올바르지 않은 형식으로 입력했습니다."),
    PRODUCT_NOT_FOUND("[ERROR] 존재하지 않는 상품입니다."),
    OVERSTOCK("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다."),
    INVALID_INPUT("[ERROR] 잘못된 입력입니다."),

    INVALID_PROMOTION("[ERROR] 올바르지 않은 형식의 프로모션입니다."),
    INVALID_PRODUCT("[ERROR] 올바르지 않은 형식의 상품입니다."),
    INVALID_ORDERITEM("[ERROR] 올바르지 않은 형식의 주문 상품입니다."),
    INVALID_ORDER("[ERROR] 올바르지 않은 형식의 주문입니다."),
    FILE_NOT_FOUND("[ERROR] 존재하지 않는 파일입니다."),
    PROMOTION_NOT_FOUND("[ERROR] 존재하지 않는 프로모션입니다."),
    INVALID_NUMBER_FORMAT("[ERROR] 숫자로 변경할 수 없는 문자열입니다."),
    INVALID_DATE_FORMAT("[ERROR] 날짜 형식으로 변경할 수 없는 문자열입니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}