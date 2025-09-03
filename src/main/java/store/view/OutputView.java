package store.view;

import java.util.List;
import java.util.Map;
import store.domain.Order;
import store.domain.Product;

public class OutputView {

    public void printProducts(Map<String, List<Product>> products) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();

        products.forEach((k, v) -> {
            v.forEach(System.out::println);
        });
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage + " 다시 입력해 주세요.");
    }

    public void printReceipt(Order order) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t수량\t금액");
        printOrderItems(order);
        System.out.println("=============증\t정===============");
        printPromotionOrderItems(order);
        System.out.println("====================================");
        printPriceInfos(order);
    }

    private static void printPriceInfos(Order order) {
        System.out.println(
                String.format("총구매액\t%d\t%,d", order.getTotalQuantity(), order.getTotalPrice())
        );
        System.out.println(String.format("행사할인\t\t-%,d", order.getTotalPromotionDiscount()));
        System.out.println(String.format("멤버십할인\t\t-%,d", order.getTotalMembershipDiscount()));
        System.out.println(String.format("내실돈\t\t%,d", order.getTotalPriceWithDiscount()));
    }

    private static void printPromotionOrderItems(Order order) {
        order.getPromotionOrderItems().forEach(promotionItem -> {
            System.out.println(
                    promotionItem.getProduct().getName() + "\t" + promotionItem.getQuantity()
            );
        });
    }

    private static void printOrderItems(Order order) {
        order.getOrderItems().forEach(orderItem ->
                System.out.println(String.format("%s\t%d\t%,d",
                        orderItem.getProduct().getName(),
                        orderItem.getQuantity(),
                        orderItem.getTotalPrice()
                )));
    }

}
