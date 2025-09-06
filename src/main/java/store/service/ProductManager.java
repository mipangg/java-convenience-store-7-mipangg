package store.service;

import java.util.ArrayList;
import java.util.Collections;
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

    // 프로모션 할인 기간이 지난 상품을 제거
    public void updatePromotionProducts() {
        products.forEach((k, v) -> removeExpiredPromotionProductsFromList(v));
    }

    // 프로모션 상품만 존재할 때 일반 상품 추가하고 프로모션 상품을 일반 상품보다 인덱스 앞에 위치시킴
    public void createRegularProductIfAbsent() {
        products.forEach((k, v) -> {
            if (v.size() == 1 && v.getFirst().isActivePromotion()) {
                v.add(genRegularProduct(v.getFirst()));
            }
            if (v.size() == 2 && !v.getFirst().isActivePromotion()) {
                Collections.reverse(v);
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

    private void removeExpiredPromotionProductsFromList(List<Product> products) {
        products.removeIf(Product::isExpiredPromotion);
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
