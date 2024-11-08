package store;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("콜라", "1000", "10", "탄산");
    }

    @Test
    @DisplayName("유저가 상품을 구매하려 할 때 재고가 충분한지 확인 후, 차감, 가격을 계산한다.")
    void testProduct() {
        assertThat(product.isAvailable(3)).isTrue();
        assertThat(product.isAvailable(11)).isFalse();
        product.reduceStock(3);
        assertThat(product.getStock()).isEqualTo(7);
        assertThat(product.getTotalPrice(3)).isEqualTo(3000);
    }

}
