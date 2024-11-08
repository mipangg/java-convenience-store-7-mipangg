package store;

public enum Error {
    INCORRECT_FORMAT("올바르지 않은 형식으로 입력했습니다."),
    NOT_EXIST("존재하지 않는 상품입니다."),
    OUT_OF_STOCK("재고 수량을 초과하여 구매할 수 없습니다."),
    INCORRECT_INPUT("잘못된 입력입니다.");

    private final String message;

    Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "[ERROR] " + message + " 다시 입력해 주세요.";
    }
}
