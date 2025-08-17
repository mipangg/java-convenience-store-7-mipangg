package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreParserTests {

    StoreParser storeParser = new StoreParser();

    @Test
    @DisplayName("콤마를 기준으로 상품 정보가 잘 분리되어 나오는지 테스트")
    void parseInfosTestForProduct() {

        List<String> productStrs = List.of(
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
                "정식도시락,6400,8,null",
                "컵라면,1700,1,MD추천상품",
                "컵라면,1700,10,null"
        );

        Map<String, String> e1 = Map.of(
                "name", "콜라",
                "price", "1000",
                "quantity", "10",
                "promotion", "탄산2+1"
        );
        Map<String, String> e2 = Map.of(
                "name", "컵라면",
                "price", "1700",
                "quantity", "10",
                "promotion", "null"
        );
        Map<String, String> e3 = Map.of(
                "name", "비타민워터",
                "price", "1500",
                "quantity", "6",
                "promotion", "null"
        );

        List<Map<String, String>> productInfos = storeParser.parseInfos(productStrs);
        assertThat(productInfos).hasSize(16);
        assertThat(productInfos.contains(e1)).isTrue();
        assertThat(productInfos.contains(e2)).isTrue();
        assertThat(productInfos.contains(e3)).isTrue();
    }

    @Test
    @DisplayName("콤마를 기준으로 프로모션 정보가 잘 분리되어 나오는지 테스트")
    void parseInfosTestForPromotion() {

        List<String> productStrs = List.of(
                "name,buy,get,start_date,end_date",
                "탄산2+1,2,1,2025-01-01,2025-12-31",
                "MD추천상품,1,1,2025-01-01,2025-12-31",
                "반짝할인,1,1,2025-08-01,2025-08-31"
        );

        Map<String, String> e1 = Map.of(
                "name", "탄산2+1",
                "buy", "2",
                "get", "1",
                "start_date", "2025-01-01",
                "end_date", "2025-12-31"
        );
        Map<String, String> e2 = Map.of(
                "name", "MD추천상품",
                "buy", "1",
                "get", "1",
                "start_date", "2025-01-01",
                "end_date", "2025-12-31"
        );
        Map<String, String> e3 = Map.of(
                "name", "반짝할인",
                "buy", "1",
                "get", "1",
                "start_date", "2025-08-01",
                "end_date", "2025-08-31"
        );

        List<Map<String, String>> productInfos = storeParser.parseInfos(productStrs);
        assertThat(productInfos).hasSize(3);
        assertThat(productInfos.contains(e1)).isTrue();
        assertThat(productInfos.contains(e2)).isTrue();
        assertThat(productInfos.contains(e3)).isTrue();
    }

}