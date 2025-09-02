package store.exception;

public enum ErrorCode {
    INVALID_FORMAT("[ERROR] 올바르지 않은 형식으로 입력했습니다."),
    PRODUCT_NOT_FOUND("[ERROR] 존재하지 않는 상품입니다."),
    OVERSTOCK("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다."),
    INVALID_INPUT("[ERROR] 잘못된 입력입니다."),

    INVALID_PROMOTION("[ERROR] 올바르지 않은 형식의 프로모션입니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getInputMessage() {
        return message + " 다시 입력해 주세요.";
    }
}