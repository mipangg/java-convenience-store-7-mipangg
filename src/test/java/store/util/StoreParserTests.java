package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreParserTests {

    private final StoreParser storeParser = new StoreParser();

    @Test
    @DisplayName("쉼표를 기준으로 문자열을 분리하여 product 정보가 담긴 map 리스트로 반환할 수 있다")
    void getParsedInfosForProductsTest() {

        List<String> infos = List.of(
                "name,price,quantity,promotion",
                "콜라,1000,10,탄산2+1",
                "콜라,1000,10,null",
                "사이다,1000,8,탄산2+1",
                "사이다,1000,7,null",
                "오렌지주스,1800,9,MD추천상품",
                "탄산수,1200,5,탄산2+1",
                "물,500,10,null",
                "비타민워터,1500,6,null",
                "감자칩,1500,5,반짝할인",
                "감자칩,1500,5,null",
                "초코바,1200,5,MD추천상품",
                "초코바,1200,5,null",
                "에너지바,2000,5,null",
                "정식도시락,6400,8,시즌지난상품",
                "정식도시락,6400,8,null",
                "컵라면,1700,1,MD추천상품",
                "컵라면,1700,10,null"
        );

        Map<String, String> expectedFirstParsedInfo = Map.of(
                "name", "콜라",
                "price", "1000",
                "quantity", "10",
                "promotion", "탄산2+1"
        );

        Map<String, String> expectedLastParsedInfo = Map.of(
                "name", "컵라면",
                "price", "1700",
                "quantity", "10",
                "promotion", "null"
        );

        List<Map<String, String>> parsedInfos = storeParser.getParsedInfos(infos);
        assertThat(parsedInfos).hasSize(17)
                .contains(expectedFirstParsedInfo)
                .contains(expectedLastParsedInfo);

    }

    @Test
    @DisplayName("쉼표를 기준으로 문자열을 분리하여 promotion 정보가 담긴 map으로 반환할 수 있다")
    void getParsedInfoForPromotionsTest() {

        List<String> infos = List.of(
                "name,buy,get,start_date,end_date",
                "탄산2+1,2,1,2025-01-01,2025-12-31",
                "MD추천상품,1,1,2025-01-01,2025-12-31",
                "반짝할인,1,1,2025-08-01,2025-08-31",
                "시즌지난상품,1,1,2025-07-01,2025-07-31"
        );

        Map<String, String> expectedFirstParsedInfo = Map.of(
                "name", "탄산2+1",
                "buy", "2",
                "get", "1",
                "start_date", "2025-01-01",
                "end_date", "2025-12-31"
        );

        List<Map<String, String>> parsedInfos = storeParser.getParsedInfos(infos);
        assertThat(parsedInfos).hasSize(4)
                .contains(expectedFirstParsedInfo);

    }

}