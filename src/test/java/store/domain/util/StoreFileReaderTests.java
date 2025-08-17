package store.domain.util;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.util.StoreFileReader;

class StoreFileReaderTests {

    private final StoreFileReader storeFileReader = new StoreFileReader();

    @Test
    @DisplayName("products 파일의 내용을 잘 불러오는지 테스트")
    void getProductsTest() {

        List<String> products = storeFileReader.getProductStrs();
        assertThat(products).hasSize(17);
        assertThat(products.getFirst()).isEqualTo("name,price,quantity,promotion");

    }

    @Test
    @DisplayName("promotions 파일의 내용을 잘 불러오는지 테스트")
    void getPromotionsTest() {

        List<String> promotions = storeFileReader.getPromotionStrs();
        assertThat(promotions).hasSize(4);
        assertThat(promotions.getFirst()).isEqualTo("name,buy,get,start_date,end_date");

    }

}