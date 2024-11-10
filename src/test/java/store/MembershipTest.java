package store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.fileReader.ProductsFileReader;
import store.model.Membership;
import store.model.OrderItem;
import store.model.Product;

public class MembershipTest {
    private Membership membership;
    private ProductsFileReader reader = new ProductsFileReader();
    private OrderItem colaItem;
    private OrderItem energyBarItem;

    @BeforeEach
    void setUp() {
        membership = new Membership();
        List<Product> products = reader.getProducts();
        Product cola = products.get(0); // 콜라,1000,10,탄산2+1
        Product energyBar = products.get(14); // 에너지바,2000,5,null

        colaItem = new OrderItem(cola, 3);
        energyBarItem = new OrderItem(energyBar, 5);
    }

    @Test
    @DisplayName("멤버십 할인을 받기 위해서는 상품의 총 구매가격이 8,000원 이상이어야 한다.")
    void beOverMinimumPriceForMembership() {
        assertThat(membership.isAvailableMembership(colaItem)).isFalse();
        assertThat(membership.isAvailableMembership(energyBarItem)).isTrue();
    }

    @Test
    @DisplayName("회원은 프로모션 미적용 금액의 30%를 할인받는다.")
    void get30PercentDiscountOfTotalPrice() {
        assertThat(membership.getMembershipDiscount(energyBarItem)).isEqualTo(3000);
    }
}
