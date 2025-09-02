package store.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.exception.ErrorCode;

public class ProductManager {

    private final Map<String, List<Product>> products = new LinkedHashMap<>();

    public void save(Product product) {
        products
                .computeIfAbsent(product.getName(), k -> new ArrayList<>())
                .add(product);
    }

    // 프로모션 상품만 존재할 때 일반 상품 추가
    public void createRegularProductIfAbsent() {
        products.forEach((k, v) -> {
            if (v.size() == 1 && v.getFirst().isActivePromotion()) {
                v.add(genRegularProduct(v.getFirst()));
            }
        });
    }

    public List<Product> getByName(String name) {
        List<Product> products = this.products.get(name);
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
        }
        return products;
    }

    public Map<String, List<Product>> findAll() {
        return new LinkedHashMap<>(products);
    }

    // 프로모션 할인 기간이 지난 상품을 제거
    public void updatePromotionProducts() {
        products.forEach((k, v) -> removeExpiredPromotionProductsFromList(v));
    }

    private void removeExpiredPromotionProductsFromList(List<Product> products) {
        products.removeIf(product -> !product.isActivePromotion());
    }

    private Product genRegularProduct(Product promotionProduct) {
        return new Product(
                promotionProduct.getName(),
                promotionProduct.getPrice(),
                0,
                null
        );
    }

}
