package store.view;

import java.util.List;
import java.util.Map;
import store.domain.Product;

public class OutputView {

    public void printProducts(Map<String, List<Product>> products) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();

        products.forEach((k, v) -> {
            v.forEach(System.out::println);
        });
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

}
