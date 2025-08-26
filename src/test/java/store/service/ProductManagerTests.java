package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;
import store.exception.ErrorCode;

class ProductManagerTests {

    private final ProductManager productManager = new ProductManager();

    @BeforeEach
    void setUp() {
        productManager.save(
                new Product("콜라",
                        1000,
                        10,
                        new Promotion(
                                "탄산2+1",
                                2,
                                1,
                                LocalDate.of(2025, 1, 1),
                                LocalDate.of(2025, 12, 31)
                        )
                )
        );
        productManager.save(
                new Product("콜라",
                        1000,
                        10,
                        null
                )
        );
        productManager.save(
                new Product("오렌지주스",
                        1800,
                        9,
                        new Promotion(
                                "MD추천상품",
                                1,
                                1,
                                LocalDate.of(2025, 1, 1),
                                LocalDate.of(2025, 12, 31)
                        )
                )
        );
        productManager.save(
                new Product("비타민워터",
                        1500,
                        6,
                        null
                )
        );
    }

    @Test
    @DisplayName("상품을 저장할 수 있다")
    void saveTest() {

        Product product = new Product("물", 500, 10, null);

        Map<String, List<Product>> oldProducts = productManager.findAll();

        productManager.save(product);

        Map<String, List<Product>> newProducts = productManager.findAll();

        assertThat(newProducts).hasSize(oldProducts.size() + 1);
        assertThat(newProducts.get(product.getName()).getFirst().getName())
                .isEqualTo(product.getName());

    }

    @Test
    @DisplayName("상품 이름으로 한 개의 특정 상품의 정보를 가져올 수 있다")
    void getByNameForSingleProductTest() {

        String targetName = "오렌지주스";

        List<Product> product = productManager.getByName(targetName);

        assertThat(product).hasSize(1);
        assertThat(product.getFirst().getName()).isEqualTo(targetName);

    }

    @Test
    @DisplayName("조회하는 상품의 프로모션 상품도 존재하면 함께 조회된다")
    void getByNameForMultipleProductTest() {

        String targetName = "콜라";

        List<Product> products = productManager.getByName(targetName);

        assertThat(products).hasSize(2);
        assertThat(products.get(0).getName()).isEqualTo(targetName);
        assertThat(products.get(1).getName()).isEqualTo(targetName);

    }

    @Test
    @DisplayName("상품 이름으로 조회되는 상품이 없다면 PRODUCT_NOT_FOUND 에러가 발생한다")
    void getByNameFailTest() {

        String targetName = "칫솔";

        assertThatThrownBy(
                () -> {
                    productManager.getByName(targetName);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.PRODUCT_NOT_FOUND.getMessage());

    }

}