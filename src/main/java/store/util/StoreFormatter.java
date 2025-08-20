package store.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.domain.Product;
import store.exception.ErrorCode;

public class StoreFormatter {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public int strToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorCode.INVALID_FORMAT.getMessage());
        }
    }

    public LocalDate strToLocalDate(String str) {
        try {
            return LocalDate.parse(str, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorCode.INVALID_FORMAT.getMessage());
        }
    }

    public Map<String, List<Product>> listToMap(List<Product> products) {
        Map<String, List<Product>> productMap =  products.stream()
                .collect(Collectors.groupingBy(
                        Product::getName,
                        LinkedHashMap:: new, // 순서 보존
                        Collectors.toList()
                ));

        addRegularProducts(productMap);
        return productMap;
    }

    // 프로모션 상품만 있는 경우 재고 없는 상태로 일반 상품 추가
    private static void addRegularProducts(Map<String, List<Product>> productMap) {
        for (Map.Entry<String, List<Product>> entry : productMap.entrySet()) {
            List<Product> value = entry.getValue();
            if (value.size() == 1 && value.getFirst().isPromotion()) {
                Product newProduct = new Product(value.getFirst().getName(),
                        value.getFirst().getPrice(), 0, null);
                value.add(newProduct);
            }
        }
    }

}
