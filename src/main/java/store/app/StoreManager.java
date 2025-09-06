package store.app;

import store.domain.Order;
import store.domain.OrderItem;
import store.domain.Product;
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
        handleOrder(order);
        // TODO: 멤버십 할인 적용 여부 입력 받기
        // TODO: order 프로모션 아이템 업데이트 및 가격 산출
//        outputView.printReceipt(order);
        askAnotherOrder();
    }

    // 주문 아이템에 따라 재고 업데이트, 재고 수량 초가 예외 발생 시 재주문
    private void handleOrder(Order order) {
        try {
            applyOrderToInventory(order);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            handleOrder(makeOrder());
        }
    }

    // TODO: 주문 아이템에 따라 재고 업데이트
    private void applyOrderToInventory(Order order) {
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            if (product.isActivePromotion() && !product.isEligibleForPromotion(quantity)) {
                askAndAddProductForPromotion(item);
            }
            if (!product.isAvailable(quantity)) {

            }
            updateInventory(item);
        }
    }

    // 상품의 재고 업데이트
    private void updateInventory(OrderItem orderItem) {
        orderItem.getProduct().updateStock(orderItem.getQuantity());
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
