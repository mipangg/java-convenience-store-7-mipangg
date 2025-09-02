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
    @DisplayName("문자열을 int 형으로 변환하여 반환할 수 있다")
    void stringToIntTest() {

        String priceStr = "1000";
        int priceInt = 1000;

        assertThat(storeFormatter.stringToInt(priceStr)).isEqualTo(priceInt);

    }

    @Test
    @DisplayName("문자열을 int 형으로 변환하는데 실패한 경우 INVALID_NUMBER_FORMAT 예외가 발생한다")
    void stringToIntFailTest() {

        String wrongStr = "유효하지않은문자열";

        assertThatThrownBy(
                () -> {
                    storeFormatter.stringToInt(wrongStr);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_NUMBER_FORMAT.getMessage());

    }

    @Test
    @DisplayName("문자열을 LocalDate 형으로 변환하여 반환할 수 있다")
    void stringToLocalDateTest() {

        String dateStr = "2025-01-01";
        LocalDate date = LocalDate.of(2025, 1, 1);

        assertThat(storeFormatter.stringToLocalDate(dateStr)).isEqualTo(date);

    }
    
    @Test
    @DisplayName("문자열을 LocalDate 형으로 변환하는데 실패한 경우 INVALID_DATE_FORMAT 예외가 발생한다")
    void stringToLocalDateFailTest() {
    
        String wrongStr = "유효하지않은형식";

        assertThatThrownBy(
                () -> {
                    storeFormatter.stringToLocalDate(wrongStr);
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_DATE_FORMAT.getMessage());
    
    }

}