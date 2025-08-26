package store.view;

import java.util.Scanner;
import store.domain.Product;
import store.exception.ErrorCode;

public class StoreInputView {
    private final Scanner sc = new Scanner(System.in);

    public String askOrder() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return sc.nextLine();
    }

    public boolean askWantMembershipDiscount() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return answerToBoolean(sc.nextLine());
    }

    public boolean askWantMoreOrder() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return answerToBoolean(sc.nextLine());
    }

    public boolean askAddPromotionProduct(Product product, int quantity) {
        System.out.println("프로모션 적용 가능한 상품에 대해 수량이 부족합니다.");
        System.out.println(product + "를 " + quantity + "개 추가하시겠습니까? (Y/N)");
        return answerToBoolean(sc.nextLine());
    }

    public boolean askNormalPurchase(Product product, int quantity) {
        System.out.println(product + "의 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 합니다.");
        System.out.println(quantity + "개를 정가로 결제하시겠습니까? (Y/N)");
        return answerToBoolean(sc.nextLine());
    }

    private boolean answerToBoolean(String answer) {
        if (answer.equalsIgnoreCase("y")) {
            return true;
        }
        if (answer.equalsIgnoreCase("n")) {
            return false;
        }
        throw new IllegalArgumentException(ErrorCode.INVALID_INPUT.getInputMessage());
    }
}
