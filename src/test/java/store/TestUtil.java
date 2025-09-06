package store;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import store.domain.Order;
import store.domain.OrderItem;
import store.domain.Product;
import store.domain.Promotion;

public class TestUtil {

    public static Promotion genPromotion() {
        return new Promotion(
                "탄산2+1",
                2,
                1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31)
        );
    }

    public static Product genPromotionProduct() {
        return new Product(
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
    }

    public static Product genProduct() {
        return new Product(
                "콜라",
                1000,
                3,
                null
        );
    }

    public static OrderItem genOrderItem() {
        return new OrderItem(
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
                ),
                6
        );
    }

    public static List<OrderItem> genOrderItems() {
        return List.of(
                new OrderItem(
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
                        ),
                        10
                ),
                new OrderItem(
                        new Product(
                                "사이다",
                                1000,
                                8,
                                new Promotion(
                                        "탄산2+1",
                                        2,
                                        1,
                                        LocalDate.of(2025, 1, 1),
                                        LocalDate.of(2025, 12, 31)
                                )
                        ),
                        3
                )
        );
    }

    public static Order genOrder() {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(
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
                ),
                10
        ));
        orderItems.add(new OrderItem(
                new Product(
                        "사이다",
                        1000,
                        8,
                        new Promotion(
                                "탄산2+1",
                                2,
                                1,
                                LocalDate.of(2025, 1, 1),
                                LocalDate.of(2025, 12, 31)
                        )
                ),
                3
        ));
        orderItems.add(new OrderItem(
                new Product(
                        "에너지바",
                        2000,
                        5,
                        null
                ),
                5
        ));
        return new Order(orderItems);
    }
}
