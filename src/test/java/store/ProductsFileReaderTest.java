package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import store.Product;
import store.ProductsFileReader;

class ProductsFileReaderTest {
    private ProductsFileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new ProductsFileReader();
    }

    @Test
    @DisplayName("상품 목록을 src/main/resources/products.md 파일에서 불러온다.")
    void getProductsFromProductsMd() {
        List<Product> products = fileReader.getProducts();

        assertThat(products).isNotEmpty();
        assertThat(products).hasSize(16);

        assertThat(products.get(0).getName()).isEqualTo("콜라");
        assertThat(products.get(0).getPrice()).isEqualTo(1000);
        assertThat(products.get(0).getStock()).isEqualTo(10);
        assertThat(products.get(0).getPromotion()).isEqualTo("탄산2+1");
    }
}
