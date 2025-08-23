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

        int expected = 1000;
        int actual = storeFormatter.strToInt("1000");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("정수형으로 바꿀 수 없는 문자열을 바꾸려하면 예외가 발생한다")
    void strToIntFailTest() {

        assertThatThrownBy(
                () -> {
                    storeFormatter.strToInt("abc");
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_NUMBER_FORMAT.getMessage());

    }

    @Test
    @DisplayName("String을 LocalDate 형으로 변환할 수 있다.")
    void strToLocalDateTest() {

        LocalDate expected = LocalDate.of(2020, 1, 1);
        LocalDate actual = storeFormatter.strToLocalDate("2020-01-01");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("LocalDate로 바꿀 수 없는 문자열로 시도할 경우 예외가 발생한다")
    void strToLocalDateFailTest() {

        assertThatThrownBy(
                () -> {
                    storeFormatter.strToLocalDate("abcd-01-01");
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_DATE_FORMAT.getMessage());

    }
}