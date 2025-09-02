package store.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import store.exception.ErrorCode;

public class StoreFormatter {

    public int stringToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorCode.INVALID_NUMBER_FORMAT.getMessage());
        }
    }

    public LocalDate stringToLocalDate(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(str, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorCode.INVALID_DATE_FORMAT.getMessage());
        }
    }

}
