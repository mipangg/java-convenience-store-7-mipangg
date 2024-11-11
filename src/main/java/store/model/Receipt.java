package store.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Receipt {
    private final String name = "==============W 편의점================";
    private final String header = "상품명\t\t\t수량\t\t\t금액";
    private final String giftLine = "===============증\t정==============";
    private final String line = "====================================";

    private StringBuilder receipt = new StringBuilder();
    private List<OrderItem> orderItems; // 일반 증정 상품
    private Map<String, Integer> gifts = new LinkedHashMap<>(); //프로모션 증정 상품
    private AmountInfo amountInfo;

    public Receipt(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    // 프로모션 상품명 + 개수
    public void addGift(OrderItem orderItem, Promotion promotion) {
        String productName = orderItem.getProduct().getName();
        int quantity = orderItem.getQuantity() / promotion.getBuyPlusGet();
        gifts.put(productName, quantity);
    }

    public void setAmountInfo(AmountInfo amountInfo) {
        this.amountInfo = amountInfo;
    }

    public String getReceipt() {
        addHeader();
        addOrderItems();
        addGiftsPart();
        addAmountPart();
        return receipt.toString();
    }

    private void addHeader() {
        receipt.append("\n");
        receipt.append(name).append("\n");
        receipt.append(header).append("\n");
    }

    private void addOrderItems() {
        for (OrderItem orderItem : orderItems) {
            String name = orderItem.getProductName();
            int quantity = orderItem.getQuantity();
            int totalPrice = orderItem.getTotalPrice();

            receipt.append(String.format("%-10s %5d %,15d\n", name, quantity, totalPrice));
        }
    }

    private void addGiftsPart() {
        receipt.append(giftLine).append("\n");
        for (Map.Entry<String, Integer> gift : gifts.entrySet()) {
            receipt.append(String.format("%-10s\t\t%d\n", gift.getKey(), gift.getValue()));
        }
    }

    private void addAmountPart() {
        receipt.append(line)
                .append("\n")
                .append(String.format("총구매액\t\t\t%d\t\t\t%,d\n", getTotalQuantity(), amountInfo.getAmount()))
                .append(String.format("행사할인\t\t\t\t\t\t-%,d\n", amountInfo.getPromotionDiscount()))
                .append(String.format("멤버십할인\t\t\t\t\t\t-%,d\n", amountInfo.getMembershipDiscount()))
                .append(String.format("내실돈\t\t\t\t\t\t %,d\n", amountInfo.getTotalAmount()));

    }

    private int getTotalQuantity() {
        int totalQuantity = 0;
        for (OrderItem orderItem : orderItems) {
            totalQuantity += orderItem.getQuantity();
        }
        return totalQuantity;
    }
}
