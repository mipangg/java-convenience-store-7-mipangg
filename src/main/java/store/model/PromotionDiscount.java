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
        boolean isPromotion = promotionProducts.containsKey(orderItem.getProductName());
        if (isPromotion) { replaceProduct(orderItem); }
        return isPromotion;
    }

    public boolean isEnoughStock(OrderItem orderItem) {
        int currentStock = promotionProducts.get(orderItem.getProductName()).getStock();
        return orderItem.getQuantity() <= currentStock;
    }

    public boolean isPromotionFormat(OrderItem orderItem) {
        Promotion promotion = getMatchedPromotion(orderItem);
        return promotion != null && promotion.isDivisible(orderItem.getQuantity());
    }

    public int calculatePromotionDiscount(OrderItem orderItem) {
        Promotion promotion = getMatchedPromotion(orderItem);
        promotionProducts.get(orderItem.getProductName()).reduceStock(orderItem.getQuantity());
        return promotion.getPromotionDiscount(orderItem.getTotalPrice());
    }

    public int calculateMaximumQuantity(OrderItem orderItem) {
        Promotion promotion = getMatchedPromotion(orderItem);
        return promotion.getMaximumQuantity(promotionProducts.get(orderItem.getProductName()).getStock());
    }

    public int getPromotionProductStock(OrderItem orderItem) {
        return promotionProducts.get(orderItem.getProductName()).getStock();
    }

    public Promotion getMatchedPromotion(OrderItem orderItem) {
        return promotions.stream()
                .filter(p -> p.getPromotionType().equals(orderItem.getProduct().getPromotion()))
                .findFirst().orElse(null);
    }

    public void reducePromotionStock(OrderItem orderItem, int quantity) {
        promotionProducts.get(orderItem.getProductName()).reduceStock(quantity);
    }

    public List<Product> getPromotionProducts() {
        return promotionProducts.values().stream().toList();
    }

    // 프로모션 제품인 경우 OrderItem의 product를 프로모션용으로 교체
    private void replaceProduct(OrderItem orderItem) {
        orderItem.setProduct(promotionProducts.get(orderItem.getProductName()));
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

    // 프로모션 제품 재고 목록 중 현재 진행중인 프로모션 제품만 업데이트
    private void currentPromotionProducts() {
        promotionProducts.values().removeIf(
                product -> ! isCurrentPromotionProduct(product));
    }

    private boolean isCurrentPromotionProduct(Product product) {
        return promotions.stream()
                .anyMatch(promotion ->
                        promotion.getPromotionType().equals(product.getPromotion()));
    }
}
