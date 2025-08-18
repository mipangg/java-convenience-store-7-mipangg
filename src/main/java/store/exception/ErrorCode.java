package store.exception;

public enum ErrorCode {
    INVALID_FORMAT("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    PRODUCT_NOT_FOUND("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    EXCEED_STOCK("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INVALID_INPUT("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."),
    FILE_NOT_FOUND("[ERROR] 파일을 불러올 수 없습니다. 경로를 확인해 주세요."),
    INVALID_ITEM("[ERROR] 잘못된 상품입니다."),
    INVALID_PROMOTION("[ERROR] 잘못된 프로모션입니다."),
    OUT_OF_ALL_STOCK("[ERROR] 재고가 모두 소진되었습니다. 판매 상품을 채워 주세요.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
