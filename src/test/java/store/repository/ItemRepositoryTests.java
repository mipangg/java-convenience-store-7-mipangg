package store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Item;
import store.domain.Promotion;

class ItemRepositoryTests {

    private final ItemRepository itemRepository = new ItemRepository();

    @BeforeEach
    void setUp() {
        itemRepository.save(
                new Item("콜라", 1000, 10,
                        new Promotion(
                                "탄산2+1",
                                2,
                                1,
                                LocalDate.of(2025, 1, 1),
                                LocalDate.of(2025, 12, 31)
                        ))
        );
        itemRepository.save(
                new Item("콜라", 1000, 10, null)
        );
        itemRepository.save(
                new Item("오렌지주스", 1800, 9,
                        new Promotion(
                                "MD추천상품",
                                1,
                                1,
                                LocalDate.of(2025, 1, 1),
                                LocalDate.of(2025, 12, 31)
                        )
                )
        );
    }

    @Test
    @DisplayName("item을 저장할 수 있다")
    void saveTest() {

        Item item = new Item("물", 500, 10, null);

        List<Item> oldItems = itemRepository.findAll();
        itemRepository.save(item);
        List<Item> newItems = itemRepository.findAll();

        assertThat(newItems).hasSize(oldItems.size() + 1);
        assertThat(newItems.getLast().getName()).isEqualTo(item.getName());
        assertThat(newItems.getLast().getPrice()).isEqualTo(item.getPrice());
    }

    @Test
    @DisplayName("item 이름으로 특정 item을 조회할 수 있다")
    void findByNameTest() {

        String targetName = "콜라";

        List<Item> foundItems = itemRepository.findByName(targetName);

        assertThat(foundItems).hasSize(2);
        assertThat(foundItems.get(0).getName()).isEqualTo(targetName);
        assertThat(foundItems.get(1).getName()).isEqualTo(targetName);

    }

}