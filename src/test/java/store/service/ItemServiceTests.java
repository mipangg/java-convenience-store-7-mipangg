package store.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Item;
import store.exception.ErrorCode;
import store.util.FakeItemRepository;

class ItemServiceTests {
    private final FakeItemRepository itemRepository = new FakeItemRepository();
    private final ItemService itemService = new ItemService(itemRepository);

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
    @DisplayName("상품들을 저장하기 위한 repository의 save를 잘 불러오는지 확인하는 테스트")
    void saveTest() {

        List<Map<String, String>> itemInfos = List.of(
                Map.of(
                        "name", "콜라",
                        "price", "1000",
                        "quantity", "10",
                        "promotion", "null"
                )
        );

        itemService.save(itemInfos);
        List<Item> savedItems = itemRepository.findAll();
        assertThat(savedItems).hasSize(4);
        assertThat(savedItems.getLast().getName()).isEqualTo("콜라");

    }

    @Test
    @DisplayName("상품의 이름으로 repository에서 상품을 잘 찾아오는지 확인하는 테스트")
    void getByNameTest() {

        String targetName = "콜라";

        List<Item> items = itemService.getByName(targetName);
        assertThat(items).hasSize(1);

    }

    @Test
    @DisplayName("존재하지 않은 상품의 이름으로 조회를 시도하면 에러가 발생한다")
    void throwWhenProductNameNotFound() {

        String targetName = "오렌지주스";
        assertThatThrownBy(
                () -> {
                    itemService.getByName(targetName);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.PRODUCT_NOT_FOUND.getMessage());

    }
}