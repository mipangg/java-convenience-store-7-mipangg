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

    @Test
    @DisplayName("프로모션 이름으로 조회할 수 있다")
    void findByNameTest() {

        String targetName = "탄산2+1";

        Promotion expectedPromotion = new Promotion("탄산2+1",
                2,
                1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31)
        );

        Promotion actualPromotion = promotionRepository.findByName(targetName);

        assertThat(actualPromotion.getName()).isEqualTo(targetName);
        assertThat(actualPromotion.getBuy()).isEqualTo(expectedPromotion.getBuy());
        assertThat(actualPromotion.getGet()).isEqualTo(expectedPromotion.getGet());

    }

    @Test
    @DisplayName("존재하지 않은 프로모션 이름으로 조회 시 null을 반환한다")
    void findByNameReturnNullTest() {

        String targetName = "존재하지않는프로모션이름";

        Promotion promotion = promotionRepository.findByName(targetName);
        assertThat(promotion).isNull();

    }

}