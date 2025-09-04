package store.app;

import java.util.ArrayList;
import java.util.List;
import store.domain.Order;
import store.domain.OrderItem;
import store.domain.Product;
import store.dto.OrderItemRequest;
import store.service.ProductManager;
import store.util.StoreMapper;
import store.util.StoreParser;
import store.view.InputView;
import store.view.OutputView;

public class OrderFactory {

    private final ProductManager productManager;
    private final StoreMapper mapper;
    private final StoreParser parser;
    private final InputView inputView;
    private final OutputView outputView;

    public OrderFactory(
            ProductManager productManager,
            StoreMapper mapper,
            StoreParser parser,
            InputView inputView,
            OutputView outputView
    ) {
        this.productManager = productManager;
        this.mapper = mapper;
        this.parser = parser;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public Order createOrder() {
        try {
            List<OrderItemRequest> orderItemRequests = parser.parseToOrderItemRequestList(getOrder());
            return new Order(createOrderItems(orderItemRequests));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return createOrder();
        }
    }

    private List<OrderItem> createOrderItems(List<OrderItemRequest> orderItemRequests) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest orderItemRequest : orderItemRequests) {
            orderItems.add(createOrderItem(orderItemRequest));
        }
        return orderItems;
    }

    private OrderItem createOrderItem(OrderItemRequest orderItemRequest) {
        Product product = productManager.getByName(orderItemRequest.name()).getFirst();
        return mapper.toOrderItem(orderItemRequest, product);
    }

    private String getOrder() {
        try {
            return inputView.askOrder();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return getOrder();
        }
    }

}
