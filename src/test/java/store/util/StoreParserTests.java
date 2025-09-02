package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

        assertThat(result.getFirst()).isEqualTo(expectedElement);

    }

}