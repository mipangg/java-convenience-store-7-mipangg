package store.view;

import java.util.Scanner;
import store.domain.Item;

public class InputView {

    private final Scanner sc = new Scanner(System.in);

    public String askShoppingList() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return sc.nextLine();
    }

    public boolean askWantMembership() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return toBoolean(sc.nextLine().trim());
    }

    public boolean askWantMoreOrder() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return toBoolean(sc.nextLine().trim());
    }

    public boolean askAddItemsForPromotion(Item item, int amount) {
        System.out.println(item.getName() + "을/를 " + amount + "개 추가하면 프로모션 할인 적용이 가능합니다.");
        System.out.println(amount + "개를 추가하시겠습니까?");
        return toBoolean(sc.nextLine().trim());
    }

    public boolean askPurchaseAtRegularPrice(Item item, int amount) {
        System.out.println(item.getName() + "의 프로모션 재고가 부족합니다.");
        System.out.println(amount + "개를 정가로 결제하시겠습니까?");
        return toBoolean(sc.nextLine().trim());
    }

    private boolean toBoolean(String input) {
        if (input.equalsIgnoreCase("y")) {
            return true;
        }
        if (input.equalsIgnoreCase("n")) {
            return false;
        }
        throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
    }
}
