package store.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    @DisplayName("String을 Map으로 반환할 수 있다")
    void parseToMapTest() {

        String row = "[콜라-10],[사이다-3]";
        Map<String, String> expectedMap = Map.of(
                "콜라", "10",
                "사이다", "3"
        );

        Map<String, String> result = storeParser.parseToMap(row);

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expectedMap);

    }

    @Test
    @DisplayName("문자열이 대괄호로 시작하고 끝나지 않으면 INVALID_FORMAT 예외가 발생한다")
    void parseToMapFailTest() {

        String invalidRow = "[콜라-10,사이다-3";

        assertThatThrownBy(
                () -> {
                    storeParser.parseToMap(invalidRow);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_FORMAT.getMessage());

    }

}