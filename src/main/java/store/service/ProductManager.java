package store.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import store.domain.Product;
import store.exception.ErrorCode;

public class ProductManager {

    private final Map<String, List<Product>> products = new HashMap<>();

    public void save(Product product) {
        List<Product> targetProduct = products.getOrDefault(product.getName(), new ArrayList<>());
        targetProduct.add(product);

        products.put(product.getName(), targetProduct);
    }

    public List<Product> getByName(String name) {
        List<Product> products = this.products.get(name);
        if (products == null) {
            throw new IllegalArgumentException(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
        }
        return products;
    }

    public Map<String, List<Product>> findAll() {
        return new HashMap<>(products);
    }

}
