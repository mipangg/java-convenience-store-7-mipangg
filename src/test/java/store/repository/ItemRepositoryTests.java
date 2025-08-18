package store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Item;

class ItemRepositoryTests {

    private final ItemRepository itemRepository = new ItemRepository();

    @BeforeEach
    void setUp() {
        itemRepository.save(new Item(
                Map.of(
                        "name", "콜라",
                        "price", "1000",
                        "quantity", "10",
                        "promotion", "탄산2+1"
                )
        ));

        itemRepository.save(new Item(
                Map.of(
                        "name", "컵라면",
                        "price", "1700",
                        "quantity", "10",
                        "promotion", "null"
                )
        ));
        itemRepository.save(new Item(
                Map.of(
                        "name", "비타민워터",
                        "price", "1500",
                        "quantity", "6",
                        "promotion", "null"
                )
        ));
    }

    @Test
    @DisplayName("item을 잘 저장할 수 있는지 테스트")
    void saveTest() {

        Item item = new Item(
                Map.of(
                        "name", "콜라",
                        "price", "1000",
                        "quantity", "10",
                        "promotion", "null"
                )
        );

        assertThat(itemRepository.findAll()).hasSize(3);

        itemRepository.save(item);

        assertThat(itemRepository.findAll()).hasSize(4);

    }

    @Test
    @DisplayName("상품 이름으로 특정 item을 잘 조회할 수 있는지 테스트")
    void findByNameTest() {

        String targetName = "콜라";

        List<Item> items = itemRepository.findByName(targetName);

        assertThat(items).hasSize(1);
        assertThat(items.getFirst().getName()).isEqualTo(targetName);

    }

    @Test
    @DisplayName("모든 상품 정보를 조회할 수 있는지 테스트")
    void findAllTest() {

        List<Item> allItems = itemRepository.findAll();

        assertThat(allItems).hasSize(3);
        assertThat(allItems.get(0).getName()).isEqualTo("콜라");
        assertThat(allItems.get(1).getName()).isEqualTo("컵라면");
        assertThat(allItems.get(2).getName()).isEqualTo("비타민워터");

    }
}