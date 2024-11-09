package store.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.fileReader.ProductsFileReader;
import store.fileReader.PromotionsFileReader;

public class PromotionDiscount {
    private List<Promotion> promotions; // 프로모션 정보를 저장
    private Map<String, Product> promotionProducts; // 프로모션 제품을 저장

    public PromotionDiscount() {
        setPromotions();
        setPromotionProducts();
    }

    public boolean isPromotion(OrderItem orderItem) {
        return promotionProducts.containsKey(orderItem.getProduct().getName());
    }

    public boolean isEnoughStock(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        int quantity = orderItem.getQuantity();
        return promotionProducts.get(product.getName()).getStock() >= quantity;
    }

    public boolean fitPromotionFormat(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        int quantity = orderItem.getQuantity();
       Promotion promotion = promotions.stream()
               .filter(p -> p.getPromotionType().equals(product.getPromotion()))
               .findFirst()
               .orElse(null);
       return promotion.isDivisible(quantity);
    }

    public int calculatePromotionDiscount(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        int quantity = orderItem.getQuantity();
        Promotion promotion = promotions.stream()
                .filter(p -> p.getPromotionType().equals(product.getPromotion()))
                .findFirst()
                .orElse(null);
        updatePromotionProducts(product, quantity);
        return promotion.getPromotionDiscount(quantity, product.getPrice());
    }

    private void updatePromotionProducts(Product product, int quantity) {
        promotionProducts.get(product.getName()).reduceStock(quantity);
    }

    private void setPromotions() {
        PromotionsFileReader reader = new PromotionsFileReader();
        promotions = reader.getPromotions().stream()
                .filter(Promotion::isAvailable)
                .collect(Collectors.toList());
    }

    private void setPromotionProducts() {
        ProductsFileReader reader = new ProductsFileReader();
        promotionProducts = reader.getProducts().stream()
                .filter(product -> !product.getPromotion().equals("null"))
                .collect(Collectors.toMap(
                        Product::getName, product -> product,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        currentPromotionProducts();
    }

    // 프로모션 리스트 중 현재 진행중인 목록만 업데이트
    private void currentPromotionProducts() {
        promotionProducts.values().removeIf(
                product -> ! isPromotionProduct(product));
    }

    public boolean isPromotionProduct(Product product) {
        return promotions.stream()
                .anyMatch(promotion ->
                        promotion.getPromotionType().equals(product.getPromotion()));
    }
}
