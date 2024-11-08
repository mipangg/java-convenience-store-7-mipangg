package store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.Promotion;
import store.PromotionsFileReader;

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

        assertThat(promotions.get(0).getPromotionType()).isEqualTo("탄산2+1");
        assertThat(promotions.get(0).getBuy()).isEqualTo(2);
        assertThat(promotions.get(0).getGet()).isEqualTo(1);
        assertThat(promotions.get(0).getStartDate()).isEqualTo("2024-01-01");
        assertThat(promotions.get(0).getEndDate()).isEqualTo("2024-12-31");
    }
}
