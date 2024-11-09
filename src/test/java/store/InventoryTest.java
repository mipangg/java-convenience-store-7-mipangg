package store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.fileReader.ProductsFileReader;
import store.model.Inventory;
import store.model.Product;

public class InventoryTest {
    Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    @DisplayName("products.md 파일을 이용하여 상품 등록한다.")
    void checkAddProduct() {
        assertThat(inventory.getProduct("오렌지주스").getName()).isEqualTo("오렌지주스");
        assertThat(inventory.getProduct("오렌지주스").getPrice()).isEqualTo(1800);
        assertThat(inventory.getProduct("오렌지주스").getStock()).isEqualTo(0);
        assertThat(inventory.getProduct("오렌지주스").getPromotion()).isEqualTo("null");
    }

    @Test
    @DisplayName("상품명으로 재고를 조회한다.")
    void retrieveStockBasedOnProductName() {
        assertThat(inventory.getStock("콜라")).isEqualTo(10);
    }

    @Test
    @DisplayName("재고가 충분한지 확인한다. 충분하지 않으면 재고가 부족함을 알린다.")
    void checkEnoughStock() {
        assertThat(inventory.checkStock("콜라",3)).isTrue();
        assertThat(inventory.checkStock("콜라",13)).isFalse();
    }

    @Test
    @DisplayName("재고가 충분할 경우 구매 후 재고를 차감한다.")
    void reduceStockWhenStockisEnough() {
        if (inventory.checkStock("콜라", 3)) {
            inventory.reduceStock("콜라", 3);
        }
        assertThat(inventory.getProduct("콜라").getStock()).isEqualTo(7);
    }

    @Test
    @DisplayName("없는 상품명으로 재고 조회를 시도할 경우 예외를 발생 시킨다.")
    void throwExceptionRetrieveStockWithNonExistingProduct() {
        assertThatThrownBy(() -> inventory.getStock("환타")
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
    }

}
