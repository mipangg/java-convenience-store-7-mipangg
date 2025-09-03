package store.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.OrderItemRequest;
import store.exception.ErrorCode;

class StoreParserTests {

    private final StoreParser storeParser = new StoreParser();

    @Test
    @DisplayName("String 리스트를 Map 리스트로 반환할 수 있다")
    void parseToMapListTest() {

        List<String> rows = List.of(
                "name,buy,get,start_date,end_date",
                "탄산2+1,2,1,2025-01-01,2025-12-31",
                "MD추천상품,1,1,2025-01-01,2025-12-31",
                "반짝할인,1,1,2025-09-01,2025-09-30",
                "시즌지난상품,1,1,2025-08-01,2025-08-31"
        );

        Map<String, String> expectedElement = Map.of(
                "name", "탄산2+1",
                "buy", "2",
                "get", "1",
                "start_date", "2025-01-01",
                "end_date", "2025-12-31"
        );

        List<Map<String, String>> result = storeParser.parseToMapList(rows);

        assertThat(result).hasSize(rows.size() - 1);
        assertThat(result.getFirst()).isEqualTo(expectedElement);

    }

    @Test
    @DisplayName("String을 OrderItemRequest 리스트로 반환할 수 있다")
    void parseToOrderItemRequestListTest() {

        String row = "[콜라-10],[사이다-3]";
        List<OrderItemRequest> expectedResult = List.of(
                new OrderItemRequest("콜라", 10),
                new OrderItemRequest("사이다", 3)
        );

        List<OrderItemRequest> result = storeParser.parseToOrderItemRequestList(row);

        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo(expectedResult.get(0));
        assertThat(result.get(1)).isEqualTo(expectedResult.get(1));

    }

    @Test
    @DisplayName("구매할 상품과 수량 형식이 올바르지 않은 경우 INVALID_FORMAT 예외가 발생한다")
    void parseToOrderItemRequestListFailTest() {

        String invalidRow = "[콜라-십],[사이다-삼]";

        assertThatThrownBy(
                () -> {
                    storeParser.parseToOrderItemRequestList(invalidRow);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_FORMAT.getMessage());

    }

}