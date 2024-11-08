package store;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.Error;

// 재고 관리
public class Inventory {
    private Map<String, Product> products = new LinkedHashMap<>();


    public Inventory(List<Product> products) {
        for (Product product : products) {
            this.products.put(product.getName(), product);
        }
        checkPromotion();
    }

    public int getStock(String productName) {
        try {
            return products.get(productName).getStock();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(Error.NOT_EXIST.getMessage());
        }
    }

    public Product getProduct(String name) {
        return this.products.get(name);
    }

    public boolean checkStock(String productName, int quantity) {
        return products.get(productName).isAvailable(quantity);
    }

    public void reduceStock(String productName, int quantity) {
        products.get(productName).reduceStock(quantity);
    }

    // 프로모션 제품만 있는 경우 재고 없음 처리
    private void checkPromotion() {
        for (Product product : this.products.values()) {
            initProduct(product);
        }
    }

    private void initProduct(Product product) {
        if (! product.getPromotion().equals("null")) {
            product.setStock(0);
            product.setPromotion("null");
        }
    }
}
