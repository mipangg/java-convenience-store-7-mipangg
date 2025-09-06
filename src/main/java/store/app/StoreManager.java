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
        updateStockByOrder(order);
        applyMembershipDiscount(order);
        applyPromotionDiscount(order);
        getTotalAmount(order);
        outputView.printReceipt(order);
        askAnotherOrder();
    }

    private void getTotalAmount(Order order) {
        order.calculateTotalPrice();
    }

    private void applyPromotionDiscount(Order order) {
        order.getOrderItems().forEach(OrderItem::setFreeQuantity);
        order.setPromotionOrderItems();
        order.calculateTotalPromotionDiscount();
    }

    private void applyMembershipDiscount(Order order) {
        if (wantMembershipDiscount()) {
            order.calculateTotalMembershipDiscount();
        }
    }

    private boolean wantMembershipDiscount() {
        try {
            return inputView.askMembershipDiscount();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return wantMembershipDiscount();
        }
    }

    private void updateStockByOrder(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        int idx = 0;
        while (idx < orderItems.size()) {
            updateStockByOrderItem(order, orderItems.get(idx));
            idx++;
        }
    }

    private void updateStockByOrderItem(Order order, OrderItem orderItem) {
        if (orderItem.getProduct().isAvailable(orderItem.getQuantity())) {
            updateStockByOrderItem(orderItem);
            return;
        }
        addSubstituteOrderItem(order, orderItem);
    }

    private void addSubstituteOrderItem(Order order, OrderItem orderItem) {
        OrderItem substituteOrderItem = findSubstituteOrderItem(orderItem);
        if (substituteOrderItem != null) {
            order.addOrderItem(substituteOrderItem);
        }
    }

    private OrderItem findSubstituteOrderItem(OrderItem orderItem) {
        Product substituteProduct = getSubstituteProduct(orderItem.getProduct());
        int shortageStock = getShortageStock(orderItem);
        validateOverStockCase(substituteProduct.isAvailable(shortageStock), true);

        updateOriginalOrderItem(orderItem, orderItem.getQuantity() - shortageStock);
        if (askRegularPurchase(orderItem, shortageStock)) {
            return new OrderItem(substituteProduct, shortageStock);
        }
        return null;
    }

    private int getShortageStock(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        int quantity = orderItem.getQuantity();
        return product.calculateShortageStock(quantity);
    }

    private void updateOriginalOrderItem(OrderItem orderItem, int quantity) {
        orderItem.setQuantity(quantity);
        orderItem.getProduct().updateStock(quantity);
    }

    private Product getSubstituteProduct(Product product) {
        validateOverStockCase(product.isActivePromotion(), true);
        Product substituteProduct = findSubstituteProduct(product);
        validateOverStockCase(substituteProduct == null, false);
        return substituteProduct;
    }

    private void validateOverStockCase(boolean expected, boolean actual) {
        if (expected != actual) {
            throw new IllegalArgumentException(ErrorCode.OVERSTOCK.getMessage());
        }
    }

    private void updateStockByOrderItem(OrderItem orderItem) {
        askAndAddProductForPromotion(orderItem);
        orderItem.getProduct().updateStock(orderItem.getQuantity());
    }

    private Product findSubstituteProduct(Product product) {
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


    private boolean askRegularPurchase(OrderItem orderItem, int shortageStock) {
        Product product = orderItem.getProduct();
        try {
            if (!product.isEligibleForPromotion(orderItem.getQuantity())) {
                return inputView.askRegularPurchase(product.getName(), shortageStock + 1);
            }
            return inputView.askRegularPurchase(product.getName(), shortageStock);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return askRegularPurchase(orderItem, shortageStock);
        }
    }

    // 프로모션 적용을 위해 상품 추가가 필요하고 사용자가 원하면 상품 추가
    private void askAndAddProductForPromotion(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        int quantity = orderItem.getQuantity();
        if (
                product.isActivePromotion()
                        && !product.isEligibleForPromotion(quantity)
                        && product.isAvailable(quantity + 1)
                        && askAnotherProductForPromotion(orderItem.getProduct().getName())
        ) {
            orderItem.addOneMoreQuantity();
        }
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
