package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreParserTests {

    private final StoreParser storeParser = new StoreParser();

    @Test
    @DisplayName("product 정보가 담긴 String 리스트를 Map 리스트로 반환할 수 있다")
    void parseInfosForProductTest() {

        List<String> strs = List.of(
                "name,price,quantity,promotion",
                "콜라,1000,10,탄산2+1",
                "콜라,1000,10,null",
                "사이다,1000,8,탄산2+1"
        );
        List<Map<String, String>> productInfos = storeParser.parseInfos(strs);

        assertThat(productInfos).hasSize(3);
        assertThat(productInfos.get(0).get("name")).isEqualTo("콜라");
        assertThat(productInfos.get(1).get("name")).isEqualTo("콜라");
        assertThat(productInfos.get(2).get("name")).isEqualTo("사이다");
    }


    @Test
    @DisplayName("promotion 정보가 담긴 String 리스트를 Map 리스트로 반환할 수 있다")
    void parseInfosForPromotionTest() {

        List<String> strs = List.of(
                "name,buy,get,start_date,end_date",
                "탄산2+1,2,1,2025-01-01,2025-12-31",
                "MD추천상품,1,1,2025-01-01,2025-12-31",
                "반짝할인,1,1,2025-08-01,2025-08-31"
        );
        List<Map<String, String>> productInfos = storeParser.parseInfos(strs);

        assertThat(productInfos).hasSize(3);
        assertThat(productInfos.get(0).get("name")).isEqualTo("탄산2+1");
        assertThat(productInfos.get(1).get("name")).isEqualTo("MD추천상품");
        assertThat(productInfos.get(2).get("name")).isEqualTo("반짝할인");
    }
}