package store.app;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.domain.Order;
import store.domain.OrderItem;
import store.domain.Product;
import store.domain.Promotion;
import store.dto.OrderItemRequest;
import store.mapper.StoreMapper;
import store.service.ProductManager;
import store.service.PromotionManager;
import store.util.StoreFileReader;
import store.util.StoreParser;
import store.view.StoreView;

public class Store {

    private final StoreFileReader fileReader;
    private final StoreParser storeParser;
    private final StoreMapper storeMapper;

    private final StoreView storeView;

    private final ProductManager productManager;
    private final PromotionManager promotionManager;

    public Store() {
        fileReader = new StoreFileReader();
        storeParser = new StoreParser();
        storeMapper = new StoreMapper();
        storeView = new StoreView();
        productManager = new ProductManager();
        promotionManager = new PromotionManager();
    }

    public void run() throws FileNotFoundException {
        setPromotions();
        setProducts();
        storeView.printProductList(productManager.findAll());

        createOrder();
    }

    private void setPromotions() throws FileNotFoundException {
        List<String> promotionInfos = fileReader.readPromotions();
        List<Map<String, String>> parsedPromotionInfos = storeParser.getParsedInfos(promotionInfos);
        for (Map<String, String> parsedPromotionInfo : parsedPromotionInfos) {
            promotionManager.save(storeMapper.toPromotion(parsedPromotionInfo));
        }
    }

    private void setProducts() throws FileNotFoundException {
        List<String> productInfos = fileReader.readProducts();
        List<Map<String, String>> parsedProductInfos = storeParser.getParsedInfos(productInfos);
        for (Map<String, String> productInfo : parsedProductInfos) {
            Promotion promotion = promotionManager.findByName(productInfo.get("promotion"));
            productManager.save(storeMapper.toProduct(productInfo, promotion));
        }
        productManager.manageInventory();
    }

    private String getOrder() {
        return storeView.askOrder();
    }

    private List<OrderItem> getOrderItems(String order) {
        List<OrderItem> orderItems = new ArrayList<>();

        List<OrderItemRequest> orderItemRequests = storeParser.getOrderItemRequests(order);
        orderItemRequests.forEach(orderItemRequest -> {
            List<Product> products = productManager.getByName(orderItemRequest.name());
            orderItems.add(storeMapper.toOrderItem(orderItemRequest, products.getFirst()));
        });

        return orderItems;
    }

    private Order createOrder() {
        String order = getOrder();
        try {
            return new Order(getOrderItems(order));

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + " 다시 입력해 주세요.");
            return createOrder();
        }
    }

    private void updateInventory(Order order) {
        order.getOrderItems().forEach(orderItem -> {
            updateProduct(orderItem);
        });
    }

    private void updateProduct(OrderItem orderItem) {
        // 1. 프로모션 상품인지 확인
        // 1-1. 프로모션 O
        // 1-2. 프로모션 X
        Product product = orderItem.getProduct();
        int quantity = orderItem.getQuantity();
        if (product.isActivePromotion()) {

            return;
        }
        product.updateStock(quantity);
    }

}
