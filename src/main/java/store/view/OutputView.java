package store.view;

import java.util.List;
import java.util.Map;
import store.domain.Order;
import store.domain.Product;

public class OutputView {

    public void printProducts(Map<String, List<Product>> products) {
        System.out.println();
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();

        products.forEach((k, v) -> v.forEach(System.out::println));
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage + " 다시 입력해 주세요.");
    }

    public void printReceipt(Order order) {
        System.out.println();
        System.out.println("==============W 편의점================");
        System.out.println(String.format("%-7s %s %7s", "상품명", "수량", "금액"));
        printOrderItems(order);
        System.out.println("=============증\t정===============");
        printPromotionOrderItems(order);
        System.out.println("====================================");
        printPriceInfos(order);
    }

    private static void printPriceInfos(Order order) {
        System.out.println(
                String.format("%-7s %-7d %,d",
                        "총구매액", order.getTotalQuantity(), order.getTotalPrice())
        );
        System.out.println(String.format("%-15s -%,d", "행사할인", order.getTotalPromotionDiscount()));
        System.out.println(String.format("%-15s -%,d",
                "멤버십할인", order.getTotalMembershipDiscount()));
        System.out.println(String.format("%-15s %,d", "내실돈", order.getTotalPriceWithDiscount()));
    }

    private static void printPromotionOrderItems(Order order) {
        order.getPromotionOrderItems().forEach(promotionItem -> System.out.println(
                String.format("%-7s %d", promotionItem.getProduct().getName(),
                        promotionItem.getFreeQuantity())
        ));
    }

    private static void printOrderItems(Order order) {
        order.getOrderItemResponses().forEach((k, v) ->
                System.out.println(
                        String.format("%-7s %d %,7d", v.getName(), v.getQuantity(), v.getPrice())
                )
        );
    }

}
