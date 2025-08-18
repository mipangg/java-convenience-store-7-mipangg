package store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;

class PromotionRepositoryTests {

    private final PromotionRepository promotionRepository = new PromotionRepository();

    @BeforeEach
    void setUp() {
        promotionRepository.save(
                new Promotion("탄산2+1",
                        2,
                        1,
                        LocalDate.of(2025,1,1),
                        LocalDate.of(2025, 12, 31)
                )
        );
        promotionRepository.save(
                new Promotion("MD추천상품",
                        1,
                        1,
                        LocalDate.of(2025,1,1),
                        LocalDate.of(2025, 12, 31)
                )
        );
        promotionRepository.save(
                new Promotion("반짝할인",
                        1,
                        1,
                        LocalDate.of(2025,8,1),
                        LocalDate.of(2025, 8, 31)
                )
        );
    }

    @Test
    @DisplayName("promotion을 저장할 수 있다")
    void saveTest() {
        Promotion promotion = new Promotion("시즌지난상품",
                1,
                1,
                LocalDate.of(2025, 7, 1),
                LocalDate.of(2025, 7, 31)
        );

        List<Promotion> oldPromotions = promotionRepository.findAll();
        promotionRepository.save(promotion);
        List<Promotion> newPromotions = promotionRepository.findAll();

        assertThat(newPromotions).hasSize(oldPromotions.size() + 1);

    }

}