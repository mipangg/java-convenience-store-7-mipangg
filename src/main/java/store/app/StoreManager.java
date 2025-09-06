package store.app;

import java.util.List;
import store.domain.Order;
import store.domain.OrderItem;
import store.domain.Product;
import store.exception.ErrorCode;
import store.service.ProductManager;
import store.util.StoreMapper;
import store.util.StoreParser;
import store.view.InputView;
import store.view.OutputView;

public class StoreManager {

    private final ProductManager productManager;
    private final StoreParser parser;
    private final StoreMapper mapper;
    private final InputView inputView;
    private final OutputView outputView;

    public StoreManager(
            ProductManager productManager,
            StoreParser parser,
            StoreMapper mapper,
            InputView inputView,
            OutputView outputView
    ) {
        this.productManager = productManager;
        this.parser = parser;
        this.mapper = mapper;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printProducts(productManager.findAll());
        Order order = makeOrder();
        applyOrderToInventory(order);
        // TODO: 멤버십 할인 적용 여부 입력 받기
//        wantMembershipDiscount();
        // TODO: order 프로모션 아이템 업데이트 및 가격 산출
//        outputView.printReceipt(order);
        askAnotherOrder();
    }

    private boolean wantMembershipDiscount() {
        try {
            return inputView.askMembershipDiscount();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return wantMembershipDiscount();
        }
    }

    private void applyOrderToInventory(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        int idx = 0;
        while (idx < orderItems.size()) {
            OrderItem item = orderItems.get(idx);
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            // 프로모션 상품인데 조건 불충족인 경우 수량 추가
            if (product.isActivePromotion() && !product.isEligibleForPromotion(quantity)) {
                askAndAddProductForPromotion(item);
            }
            // 재고가 충분하지 않은 경우
            if (!product.isAvailable(quantity)) {
                Product substituteProduct = getSubstituteProduct(product);
                if (substituteProduct == null) {
                    throw new IllegalArgumentException(ErrorCode.OVERSTOCK.getMessage());
                }
                int shortageStock = product.calculateShortageStock(quantity);
                item.setQuantity(quantity - shortageStock);
                if (askRegularPurchase(product.getName(), shortageStock)) {
                    order.addOrderItem(new OrderItem(substituteProduct, shortageStock));
                }
            }
            applyOrderItemToInventory(item);
            idx++;
        }
    }

    private Product getSubstituteProduct(Product product) {
        List<Product> products = productManager.getByName(product.getName());
        if (
                product.isActivePromotion()
                && products.size() == 2
                && !products.getLast().isActivePromotion()
        ) {
            return products.getLast();
        }
        return null;
    }

    // 주문 아이템을 상품 재고에 반영
    private void applyOrderItemToInventory(OrderItem orderItem) {
        orderItem.getProduct().updateStock(orderItem.getQuantity());
    }

    private boolean askRegularPurchase(String productName, int shortageStock) {
        try {
            return inputView.askRegularPurchase(productName, shortageStock);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return askRegularPurchase(productName, shortageStock);
        }
    }

    // 프로모션을 위해 상품 추가
    private void askAndAddProductForPromotion(OrderItem orderItem) {
        if (!askAnotherProductForPromotion(orderItem.getProduct().getName())) {
            return;
        }
        orderItem.addOneMoreQuantity();
    }

    private boolean askAnotherProductForPromotion(String productName) {
        try {
            return inputView.askAddProduct(productName);
        } catch (IllegalStateException e) {
            outputView.printErrorMessage(e.getMessage());
            return askAnotherProductForPromotion(productName);
        }
    }

    private void askAnotherOrder() {
        try {
            if (inputView.askAnotherOrder()) {
                run();
            }
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            askAnotherOrder();
        }
    }

    private Order makeOrder() {
        OrderFactory orderFactory = new OrderFactory(
                productManager,
                mapper,
                parser,
                inputView,
                outputView
        );
        return orderFactory.createOrder();
    }

}
