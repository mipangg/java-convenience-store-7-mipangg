package store.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.fileReader.ProductsFileReader;
import store.model.Product;

public class Inventory {
    private List<Product> totalProducts; // 초기 제품들 저장
    private Map<String, Product> products = new LinkedHashMap<>(); // 일반 제품 저장

    public Inventory() {
        ProductsFileReader productsFileReader = new ProductsFileReader();
        totalProducts = productsFileReader.getProducts();
        for (Product product : totalProducts) {
            this.products.put(product.getName(), product);
        }
    }

    public Product getProduct(String name) {
        return this.products.get(name);
    }

    public List<Product> getTotalProducts() {
        return this.totalProducts;
    }

    public boolean checkStock(String productName, int quantity) {
        return products.get(productName).isAvailable(quantity);
    }

    public void reduceStock(String productName, int quantity) {
        products.get(productName).reduceStock(quantity);
    }

    public List<Product> getRegularProducts() {
        return products.values().stream().toList();
    }
}
