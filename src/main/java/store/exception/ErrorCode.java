package store.exception;

public enum ErrorCode {
    INVALID_ORDER("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    PRODUCT_NOT_FOUND("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    EXCEED_STOCK("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INVALID_INPUT("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."),

    FILE_NOT_FOUND("[ERROR] 파일을 찾을 수 없습니다."),
    INVALID_NUMBER_FORMAT("[ERROR] 숫자로 변환할 수 없는 문자열입니다."),
    INVALID_DATE_FORMAT("[ERROR] 날짜 형식으로 변환할 수 없는 문자열입니다."),
    INVALID_PRODUCT("[ERROR] 잘못된 상품입니다."),
    INVALID_PROMOTION("[ERROR] 잘못된 프로모션입니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
