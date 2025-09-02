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
                new Product(
                        "콜라",
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
                new Product(
                        "콜라",
                        1000,
                        10,
                        null
                )
        );

        productManager.save(
                new Product(
                        "오렌지주스",
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
                new Product(
                        "비타민워터",
                        1500,
                        6,
                        null
                )
        );
    }

    @Test
    @DisplayName("모든 판매 상품들을 조회할 수 있다")
    void findAllTest() {

        int expectedSize = 3;

        Map<String, List<Product>> products = productManager.findAll();

        assertThat(products).hasSize(expectedSize);

    }

    @Test
    @DisplayName("판매 상품을 등록할 수 있다")
    void saveProductTest() {

        Product product = new Product("물", 500, 10, null);

        Map<String, List<Product>> oldProducts = productManager.findAll();
        productManager.save(product);
        Map<String, List<Product>> updatedProducts = productManager.findAll();

        assertThat(updatedProducts).hasSize(oldProducts.size() + 1);
        assertThat(oldProducts.get(product.getName())).isNull();
        assertThat(updatedProducts.get(product.getName())).isNotNull();

    }

    @Test
    @DisplayName("상품 이름으로 판매 상품을 조회할 수 있다")
    void getByNameTest() {

        String targetName = "콜라";

        List<Product> products = productManager.getByName(targetName);

        assertThat(products).hasSize(2);
        assertThat(products.getFirst().getName()).isEqualTo(targetName);
        assertThat(products.getFirst().isActivePromotion()).isTrue();
        assertThat(products.getLast().getName()).isEqualTo(targetName);
        assertThat(products.getLast().isActivePromotion()).isFalse();

    }

    @Test
    @DisplayName("프로모션 상품만 존재하면, 일반 상품의 재고가 없더라도 저장한다")
    void createRegularProductIfAbsentTest() {

        String targetName = "오렌지주스";

        List<Product> oldProducts = productManager.getByName(targetName);
        assertThat(oldProducts).hasSize(1);
        assertThat(oldProducts.getLast().isActivePromotion()).isTrue();

        productManager.createRegularProductIfAbsent();

        List<Product> updatedProducts = productManager.getByName(targetName);
        assertThat(updatedProducts).hasSize(2);
        assertThat(updatedProducts.getLast().isActivePromotion()).isFalse();

    }

    @Test
    @DisplayName("프로모션 할인 기간이 지난 상품을 제거할 수 있다")
    void updatePromotionProductsTest() {

        String targetName = "정식도시락";
        productManager.save(
                new Product(
                        "정식도시락",
                        6400,
                        8,
                        new Promotion(
                                "시즌지난상품",
                                1,
                                1,
                                LocalDate.of(2025, 8, 1),
                                LocalDate.of(2025, 8, 31)
                        )
                )
        );

        assertThat(productManager.getByName(targetName)).isNotNull();

        productManager.updatePromotionProducts();

        assertThatThrownBy(
                () -> {
                    productManager.getByName(targetName);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.PRODUCT_NOT_FOUND.getMessage());

    }

}