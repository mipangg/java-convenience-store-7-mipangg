package store.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Item;
import store.util.FakeItemRepository;

class ItemServiceTests {
    private final FakeItemRepository itemRepository = new FakeItemRepository();
    private final ItemService itemService = new ItemService(itemRepository);

    @Test
    @DisplayName("상품들을 저장하기 위한 repository의 save를 잘 불러오는지 확인하는 테스트")
    void saveTest() {

        List<Map<String, String>> itemInfos = List.of(
                Map.of(
                        "name", "콜라",
                        "price", "1000",
                        "quantity", "10",
                        "promotion", "탄산2+1"
                ),
                Map.of(
                        "name", "컵라면",
                        "price", "1700",
                        "quantity", "10",
                        "promotion", "null"
                ),
                Map.of(
                        "name", "비타민워터",
                        "price", "1500",
                        "quantity", "6",
                        "promotion", "null"
                )
        );

        itemService.save(itemInfos);
        List<Item> savedItems = itemRepository.findAll();
        assertThat(savedItems).hasSize(3);
        assertThat(savedItems.get(0).getName()).isEqualTo("콜라");
        assertThat(savedItems.get(1).getName()).isEqualTo("컵라면");
        assertThat(savedItems.get(2).getName()).isEqualTo("비타민워터");

    }
}