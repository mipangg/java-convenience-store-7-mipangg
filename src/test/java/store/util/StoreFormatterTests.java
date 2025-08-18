package store.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.exception.ErrorCode;

class StoreFormatterTests {

    private final StoreFormatter storeFormatter = new StoreFormatter();

    @Test
    @DisplayName("String을 int 형으로 변환할 수 있다")
    void strToIntTest() {

        String targetStr = "1000";
        int expectedInt = 1000;

        int actualInt = storeFormatter.strToInt(targetStr);

        assertThat(actualInt).isEqualTo(expectedInt);

    }

    @Test
    @DisplayName("int로 변환시키려는 String이 숫자로 이루어지지 않으면 에러가 발생한다")
    void throwWhenStringIsNotNumber() {

        String wrongStr = "price";

        assertThatThrownBy(
                () -> {
                    storeFormatter.strToInt(wrongStr);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_FORMAT.getMessage());

    }

    @Test
    @DisplayName("String을 LocalDate 형식으로 변환할 수 있다")
    void strToLocalDateTest() {

        String targetStr = "2025-08-18";
        LocalDate expectedLocalDate = LocalDate.of(2025, 8, 18);

        LocalDate actualLocalDate = storeFormatter.strToLocalDate(targetStr);

        assertThat(actualLocalDate).isEqualTo(expectedLocalDate);

    }

    @Test
    @DisplayName("LocalDate로 변환하려는 String이 올바르지 않은 형식이면 에러가 발생한다")
    void throwWhenStringIsWrongLocalDateFormat() {

        String wrongStr = "2025/08/18";

        assertThatThrownBy(
                () -> {
                    storeFormatter.strToLocalDate(wrongStr);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_FORMAT.getMessage());

    }

}