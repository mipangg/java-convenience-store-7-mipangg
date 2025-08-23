package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreFileReaderTests {

    private final StoreFileReader storeFileReader = new StoreFileReader();

    @Test
    @DisplayName("products.md 파일을 잘 불러올 수 있다")
    void readProductsTest() throws Exception {

        String expectedFirstLine = "name,price,quantity,promotion";
        String expectedLastLine = "컵라면,1700,10,null";

        List<String> actualFileLines = storeFileReader.readProducts();

        assertThat(actualFileLines.getFirst()).isEqualTo(expectedFirstLine);
        assertThat(actualFileLines.getLast()).isEqualTo(expectedLastLine);
    }

    @Test
    @DisplayName("promotions.md 파일을 잘 불러올 수 있다")
    void readPromotionsTest() throws Exception {

        String expectedFirstLine = "name,buy,get,start_date,end_date";
        String expectedLastLine = "시즌지난상품,1,1,2025-07-01,2025-07-31";

        List<String> actualFileLines = storeFileReader.readPromotions();

        assertThat(actualFileLines.getFirst()).isEqualTo(expectedFirstLine);
        assertThat(actualFileLines.getLast()).isEqualTo(expectedLastLine);

    }

}