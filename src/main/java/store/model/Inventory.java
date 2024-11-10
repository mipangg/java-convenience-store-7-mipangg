package store.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.Error;
import store.fileReader.ProductsFileReader;

// 재고 관리
public class Inventory {
    private final Map<String, Product> products = new LinkedHashMap<>(); // 일반 제품 저장

    public Inventory() {
        ProductsFileReader productsFileReader = new ProductsFileReader();
        List<Product> products = productsFileReader.getProducts();
        for (Product product : products) {
            this.products.put(product.getName(), product);
        }
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
}
