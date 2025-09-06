package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreFileReaderTests {

    private final StoreFileReader storeFileReader = new StoreFileReader();

    @Test
    @DisplayName("products.md에서 상품 목록을 불러와 String 리스트로 변환해 반환할 수 있다")
    void readProductsTest() {

        List<String> rows = storeFileReader.readProducts();
        String header = "name,price,quantity,promotion";
        int size = 18;

        assertThat(rows).isNotEmpty();
        assertThat(rows).hasSize(size);
        assertThat(rows.getFirst()).isEqualTo(header);

    }

    @Test
    @DisplayName("promotions.md에서 프로모션 목록을 불러와 String 리스트로 변환해 반환할 수 있다")
    void readPromotionsTest() {

        List<String> rows = storeFileReader.readPromotions();
        String header = "name,buy,get,start_date,end_date";
        int size = 5;

        assertThat(rows).isNotEmpty();
        assertThat(rows).hasSize(size);
        assertThat(rows.getFirst()).isEqualTo(header);

    }

}