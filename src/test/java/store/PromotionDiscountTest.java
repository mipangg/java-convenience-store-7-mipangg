package store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.fileReader.ProductsFileReader;
import store.model.OrderItem;
import store.model.Product;
import store.model.PromotionDiscount;

public class PromotionDiscountTest {
    private PromotionDiscount promotionDiscount = new PromotionDiscount();
    private ProductsFileReader reader = new ProductsFileReader();
    private List<OrderItem> orderItems;

    @BeforeEach
    void setUp() {
        List<Product> products = reader.getProducts();
        Product cola = products.get(0); // 콜라,1000,10,탄산2+1
        Product energyBar = products.get(15); // 에너지바,2000,5,null
        Product potatoChip = products.get(10); // 감자칩,1500,5,반짝할인
        Product chocolateBar = products.get(12); // 초코바,1200,5,MD추천상품
        Product soda = products.get(6); // 탄산수,1200,5,탄산2+1

        OrderItem colaItem = new OrderItem(cola, 3);
        OrderItem energyBarItem = new OrderItem(energyBar, 5);
        OrderItem potatoChipItem = new OrderItem(potatoChip, 6);
        OrderItem chocolateBarItem = new OrderItem(chocolateBar, 1);
        OrderItem sodaItem = new OrderItem(soda, 3);

        orderItems = new ArrayList<>();
        orderItems.add(colaItem);
        orderItems.add(energyBarItem);
        orderItems.add(potatoChipItem);
        orderItems.add(chocolateBarItem);
        orderItems.add(sodaItem);
    }

    @Test
    @DisplayName("프로모션 제품인지 확인한다.")
    void checkIsPromotion() {
        assertThat(promotionDiscount.isPromotion(orderItems.get(0))).isTrue();
        assertThat(promotionDiscount.isPromotion(orderItems.get(1))).isFalse();
        assertThat(promotionDiscount.isPromotion(orderItems.get(2))).isTrue();
        assertThat(promotionDiscount.isPromotion(orderItems.get(3))).isTrue();
        assertThat(promotionDiscount.isPromotion(orderItems.get(4))).isTrue();
    }

    @Test
    @DisplayName("프로모션 상품의 재고가 충분한지 확인한다.")
    void checkIfPromotionProductsAreEnough() {
        assertThat(promotionDiscount.isEnoughStock(orderItems.get(0))).isTrue();
        assertThat(promotionDiscount.isEnoughStock(orderItems.get(2))).isFalse();
        assertThat(promotionDiscount.isEnoughStock(orderItems.get(3))).isTrue();
        assertThat(promotionDiscount.isEnoughStock(orderItems.get(4))).isTrue();
    }

    @Test
    @DisplayName("프로모션 적용 기준에 맞는 수량인지 확인한다.")
    void checkIfNeedMoreQauntityForPromotion() {
        assertThat(promotionDiscount.fitPromotionFormat(orderItems.get(0))).isTrue();
        assertThat(promotionDiscount.fitPromotionFormat(orderItems.get(3))).isFalse();
        assertThat(promotionDiscount.fitPromotionFormat(orderItems.get(4))).isTrue();
    }

    @Test
    @DisplayName("프로모션 할인 금액을 계산하고 프로모션 재고를 차감한다.")
    void calculatePromotionDiscountAndReducePromotionStock() {
        assertThat(promotionDiscount.calculatePromotionDiscount(orderItems.get(0))).isEqualTo(2000);
        assertThat(promotionDiscount.calculatePromotionDiscount(orderItems.get(0))).isEqualTo(2000);
        assertThat(promotionDiscount.calculatePromotionDiscount(orderItems.get(4))).isEqualTo(2400);
    }
}
