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

    // 재고 없는 일반 상품을 업데이트하고 기간 지난 프로모션 상품을 삭제하기 위한 메서드
    public void manageInventory() {
        products.forEach((k, v) -> {
            Product product = v.getFirst();
            if (product.isPromotion() && !product.isActivePromotion()) {
                v.remove(product);
            }
            if (v.size() == 1 && product.isActivePromotion()) {
                v.add(product.getNormalProduct());
            }
        });
    }

    public List<Product> getByName(String name) {
        List<Product> targetProducts = this.products.get(name);
        if (targetProducts == null) {
            throw new IllegalArgumentException(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
        }
        return targetProducts;
    }

    public Map<String, List<Product>> findAll() {
        return new LinkedHashMap<>(products);
    }
}
