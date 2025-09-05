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
        updateInventory(order);
        // TODO: 멤버십 할인 적용 여부 입력 받기
        // TODO: order 프로모션 아이템 업데이트 및 가격 산출
//        outputView.printReceipt(order);
        // TODO: 추가 구매 여부 입력 받기
    }

    // TODO: 주문 아이템에 따라 재고 업데이트
    private void updateInventory(Order order) {
        order.getOrderItems().forEach(item -> {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            // 1. 프로모션 상품인 경우 조건 맞는지 확인(product)
            // 1-1. O -> 2.로 진행
            // 1-2. X -> 수량 추가 여부 받기(inputView)

            // 2. 재고 수량 < 구매 수량인지 확인(product)
            // 2-1. O -> 구매 수량 차감(product)
            // 2-2. X -> 3.로 진행

            // 3. 대체 상품이 있는지 확인
            // 3-1. O -> 일부 수량 일반 상품 결제 여부 받기(inputView)
            // 3-1-1. O -> 4.로 진행
            // 3-1-2. X -> 포용가능한 수량으로 구매 수량 및 상품 재고 업데이트
            // 3-2. X -> 예외 발생(초과 수량)

            // 4. 구매 수량 - 재고 수량 받기(product) -> 새 OrderItem 생성 후 추가(주문)
        });
    }

    private void checkPromotionCondition(OrderItem orderItem) {
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
