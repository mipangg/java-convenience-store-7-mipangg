package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreFileReaderTests {

    private final StoreFileReader storeFileReader = new StoreFileReader();

    @Test
    @DisplayName("products.md 파일 내용을 String List로 반환할 수 있다")
    void getProductStrsTest() {

        String expectedHeader = "name,price,quantity,promotion";
        List<String> productStrs = storeFileReader.getProductStrs();

        assertThat(productStrs).hasSize(17);
        assertThat(productStrs.getFirst()).isEqualTo(expectedHeader);

    }

    @Test
    @DisplayName("promotions.md 파일 내용을 String List로 변환할 수 있다")
    void getPromotionStrsTest() {

        String expectedHeader = "name,buy,get,start_date,end_date";
        List<String> promotionStrs = storeFileReader.getPromotionStrs();

        assertThat(promotionStrs).hasSize(4);
        assertThat(promotionStrs.getFirst()).isEqualTo(expectedHeader);

    }

}