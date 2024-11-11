package store.model;

import java.util.ArrayList;
import java.util.List;
import store.Validator;

public class Order {
    private final boolean CHECK = true;
    private final boolean PASS = false;

    private Validator validator;
    private InputModel inputModel;
    private Inventory inventory; // 일반 재고
    private PromotionDiscount promotionDiscount; // 프로모션 재고
    private AmountInfo amountInfo;// 금액 정보

    private List<OrderItem> orderItems;
    private Receipt receipt;
    private boolean flag;

    public Order() {
        validator = new Validator();
        inputModel = new InputModel();
        inventory = new Inventory();
        promotionDiscount  = new PromotionDiscount();
        amountInfo = new AmountInfo();
    }

    public void setOrderItems(List<String> userOrders) {
        List<OrderItem> userOrderItems = new ArrayList<>();
        for (String order : userOrders) {
            String[] items = order.split("-");
            Product product = validator.validateProduct(inventory.getProduct(items[0]));
            userOrderItems.add(new OrderItem(product, validator.validateQuantity(items[1])));
        }
        orderItems = userOrderItems;
        setReceipt();
    }

    public String getReceipt() { return receipt.getReceipt(); }

    public List<OrderItem> getOrderItems() { return orderItems; }

    public void calculateTotalAmountWithDiscount() {
        validateEnoughStock();
        applyPromotionDiscount();
        applyMembershipDiscount();
        calculateTotalAmount();
        receipt.setAmountInfo(amountInfo);
    }

    private void validateEnoughStock() {
        for (OrderItem orderItem : orderItems) {
            int stock  = inventory.getProduct(orderItem.getProductName()).getStock();
            stock += getPromotionProductStock(orderItem);
            validator.validateStock(orderItem, stock);
        }
    }

    private int getPromotionProductStock(OrderItem orderItem) {
        int stock = 0;
        if (promotionDiscount.isPromotion(orderItem)) {
            stock = inventory.getProduct(orderItem.getProductName()).getStock();
        }
        return stock;
    }

    private void applyPromotionDiscount() {
        for (OrderItem orderItem : orderItems) {
            getPromotionDiscount(orderItem);
        }
    }

    private void applyMembershipDiscount() {
        flag = inputModel.wantMembershipDiscount();
        for (OrderItem orderItem : orderItems) {
            amountInfo.addMembershipDiscount(getMembershipDiscount(orderItem));
        }
    }

    private void calculateTotalAmount() {
        for (OrderItem orderItem : orderItems) {
            amountInfo.addAmount(orderItem.getTotalPrice());
        }
    }

    // 멤버십 할인 금액 반환
    private int getMembershipDiscount(OrderItem orderItem) {
        Membership membership = new Membership();

        if (! flag) { return 0; }
        if (! membership.isAvailableMembership(orderItem)) { return 0; }

        return membership.getMembershipDiscount(orderItem);
    }

    private void getPromotionDiscount(OrderItem orderItem) {
        if (checkIsPromotion(orderItem) == CHECK) { return; }
        if (checkOutOfStock(orderItem) == CHECK) { return; }
        if (checkQuantityFormat(orderItem) == CHECK) { return;}
        int discountAmount = promotionDiscount.calculatePromotionDiscount(orderItem);
        amountInfo.addPromotionDiscount(discountAmount);
        receipt.addGift(orderItem, promotionDiscount.getMatchedPromotion(orderItem));
    }

    private boolean checkIsPromotion(OrderItem orderItem) {
        if (! promotionDiscount.isPromotion(orderItem)) {
            inventory.reduceStock(orderItem.getProductName(), orderItem.getQuantity());
            return CHECK;
        }
        return PASS;
    }

    // 프로모션 제품 재고가 부족한지 검사
    private boolean checkOutOfStock(OrderItem orderItem) {
        if (! orderItem.getProduct().isAvailable(orderItem.getQuantity())) {
            askBuyRegulaProducts(orderItem);
            return CHECK;
        }
        return PASS;
    }

    // 프로모션 행사 수량에 적합한지 검사
    private boolean checkQuantityFormat(OrderItem orderItem) {
        if (! promotionDiscount.isPromotionFormat(orderItem)) {
            askAdditionalQuantity(orderItem);
            return CHECK;
        }
        return PASS;
    }

    private void askBuyRegulaProducts(OrderItem orderItem) {
        int quantity = orderItem.getQuantity();
        int maximumQuantity = promotionDiscount.calculateMaximumQuantity(orderItem);

        orderItem.setQuantity(maximumQuantity);
        receipt.addGift(orderItem, promotionDiscount.getMatchedPromotion(orderItem));
        amountInfo.addPromotionDiscount(getDiscountAmount(orderItem));
        confirmRegularPurchase(orderItem, quantity, maximumQuantity);
    }

    private void confirmRegularPurchase(OrderItem orderItem, int quantity, int maximumQuantity) {
        if(inputModel.wantToBuyRegularPrice(orderItem.getProductName(), quantity - maximumQuantity)) {
            orderItem.setProduct(inventory.getProduct(orderItem.getProductName()));

            int restQuantity = quantity - maximumQuantity - getRestPromotionStock(orderItem);
            inventory.reduceStock(orderItem.getProductName(), restQuantity);
            orderItem.setQuantity(quantity);
        }
    }

    private int getRestPromotionStock(OrderItem orderItem) {
        int restPromotionStock = promotionDiscount.getPromotionProductStock(orderItem);
        promotionDiscount.reducePromotionStock(orderItem, restPromotionStock);
        return restPromotionStock;
    }

    private int getDiscountAmount(OrderItem orderItem) {
        return promotionDiscount.calculatePromotionDiscount(orderItem);
    }

    private void askAdditionalQuantity(OrderItem orderItem) {
        if(inputModel.wantToAddMoreProduct(orderItem.getProductName())) {
            orderItem.setQuantity(orderItem.getQuantity() + 1);
            receipt.addGift(orderItem, promotionDiscount.getMatchedPromotion(orderItem));
            amountInfo.addPromotionDiscount(getDiscountAmount(orderItem));
        }
    }

    private void setReceipt() { receipt = new Receipt(orderItems); }

    public void showAllProducts() {
        List<Product> regularProducts = inventory.getRegularProducts();
        List<Product> promotionProducts = promotionDiscount.getPromotionProducts();
        regularProducts.stream()
                .map(Product::toString).forEach(System.out::println);
        promotionProducts.stream()
                .map(Product::toString).forEach(System.out::println);
    }
}
