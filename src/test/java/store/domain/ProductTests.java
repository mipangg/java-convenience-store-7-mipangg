package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.TestUtil;
import store.exception.ErrorCode;

class ProductTests {

    @Test
    @DisplayName("상품 이름, 가격, 재고 수량, 프로모션 정보를 저장할 수 있다")
    void createProductTest() {

        String name = "콜라";
        int price = 1000;
        int stock = 10;
        Promotion promotion = TestUtil.genPromotion();

        Product product = new Product(name, price, stock, promotion);
        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
    }

    @Test
    @DisplayName("상품이 활성 상태인 프로모션 상품인지 아닌지를 반환할 수 있다")
    void isActivePromotionTest() {

        Product promotionProduct = TestUtil.genPromotionProduct();

        assertThat(promotionProduct).isNotNull();
        assertThat(promotionProduct.isActivePromotion()).isTrue();

    }

    @Test
    @DisplayName("빈 이름, 0 보다 작은 가격이나 재고 수량으로 생성을 시도하면 INVALID_PRODUCT 예외가 발생한다")
    void createProductFailTest() {

        String name = "";
        int price = 1000;
        int stock = 10;

        assertThatThrownBy(
                () -> {
                    new Product(name, price, stock, null);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_PRODUCT.getMessage());
    }

    @Test
    @DisplayName("재고 수량이 구매 수량보다 크거나 같은지 반환할 수 있다")
    void isAvailableTest() {

        Product product = new Product("콜라", 1000, 10, null);
        int quantity = 3;

        assertThat(product.isAvailable(quantity)).isTrue();

    }

    @Test
    @DisplayName("구매 수량을 차감하여 재고 수량을 업데이트할 수 있다")
    void updateStockTest() {

        Product product = new Product("콜라", 1000, 10, null);
        int quantity = 3;
        int expectedUpdateStock = 7;

        product.updateStock(quantity);
        assertThat(product.toString()).contains(Integer.toString(expectedUpdateStock));

    }

    @Test
    @DisplayName("구매 수량에 재고 수량을 뺀 값을 반환할 수 있다")
    void calculateShortageStockTest() {

        Product product = TestUtil.genPromotionProduct();
        int quantity = 3;
        int expectedQuantity = -7;

        assertThat(product.calculateShortageStock(quantity)).isEqualTo(expectedQuantity);

    }

    @Test
    @DisplayName("상품별 총 구매 수량에서 무료 증정되는 상품 개수를 반환할 수 있다")
    void getTotalFreeQuantityTest() {

        Product product = new Product(
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
        );

        int quantity = 6;
        int freeQuantity = 2;

        assertThat(product.getTotalFreeQuantity(quantity)).isEqualTo(freeQuantity);

    }
}