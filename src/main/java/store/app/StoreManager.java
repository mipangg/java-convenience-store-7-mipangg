package store.app;

import store.domain.Order;
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
//        outputView.printReceipt(order);
    }

    private void updateInventory(Order order) {

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
