package store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.fileReader.PromotionsFileReader;
import store.model.Promotion;

class PromotionsFileReaderTest {
    private PromotionsFileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new PromotionsFileReader();
    }

    @Test
    @DisplayName("행사 목록을 src/main/resources/promotions.md 파일에서 불러온다.")
    void getPromotionsFromPromotionsMd() {
        List<Promotion> promotions = fileReader.getPromotions();

        assertThat(promotions).isNotEmpty();
        assertThat(promotions).hasSize(3);

        assertThat(promotions.getFirst().getPromotionType()).isEqualTo("탄산2+1");
    }
}
