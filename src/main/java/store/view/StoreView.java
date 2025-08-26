package store.view;

import java.util.List;
import java.util.Scanner;
import store.domain.Order;
import store.domain.OrderItem;
import store.domain.Product;
import store.exception.ErrorCode;

public class StoreView {
    private final Scanner sc = new Scanner(System.in);

    public void printProductList(List<Product> products) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");

        for (Product product : products) {
            System.out.println(product);
        }
    }

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

    public boolean askAddPromotionProduct(String productName) {
        System.out.println("현재 " + productName
                + "은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        return answerToBoolean(sc.nextLine());
    }

    public boolean askNormalPurchase(String productName, int quantity) {
        System.out.println("현재 " + productName + " " + quantity
                + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        return answerToBoolean(sc.nextLine());
    }

    public void printReceipt(Order order) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t수량\t금액");

        System.out.println("=============증\t정===============");
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
