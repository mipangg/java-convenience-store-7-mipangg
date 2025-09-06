package store.app;

import store.domain.Order;
import store.domain.OrderItem;
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
        // TODO: 재고 수량 초과 시 재주문 입력 기능처리 필요
//        applyOrderToInventory(order);
        // TODO: 멤버십 할인 적용 여부 입력 받기
        // TODO: order 프로모션 아이템 업데이트 및 가격 산출
//        outputView.printReceipt(order);
        askAnotherOrder();
    }

    // TODO: 주문 아이템에 따라 재고 업데이트
    private void applyOrderToInventory(Order order) {
        for (OrderItem item : order.getOrderItems()) {

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
