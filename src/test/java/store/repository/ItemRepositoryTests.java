package store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Item;

class ItemRepositoryTests {

    private final ItemRepository itemRepository = new ItemRepository();

    @Test
    @DisplayName("item을 잘 저장할 수 있는지 테스트")
    void saveTest() {

        Item item = new Item(
                Map.of(
                        "name", "콜라",
                        "price", "1000",
                        "quantity", "10",
                        "promotion", "탄산2+1"
                )
        );

        assertThat(itemRepository.findAll()).isEmpty();

        itemRepository.save(item);

        assertThat(itemRepository.findAll()).hasSize(1);

    }

}