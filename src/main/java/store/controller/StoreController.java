package store.controller;

import java.util.List;
import store.model.InputModel;
import store.model.Order;
import store.model.Product;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    InputView inputView;
    OutputView outputView;
    InputModel inputModel;
    Order order;

    public StoreController() {
        inputView = new InputView();
        outputView = new OutputView();
        inputModel = new InputModel();
        order = new Order();
    }

    public void init() {
        outputView.printProducts();
    }

    public void run() {
        set();
        outputView.printReceipt(order.getReceipt());
        if (inputModel.wantMoreShopping()) {
            outputView.printUpdateProducts();
            order.showAllProducts();
            run();
        }
    }

    private void set() {
        try {
            List<String> orderItems = inputModel.getUserOrders(inputView.readItem());
            order.setOrderItems(orderItems);
            order.calculateTotalAmountWithDiscount();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            set();
        }
    }
}
