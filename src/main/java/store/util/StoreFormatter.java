package store.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import store.exception.ErrorCode;

public class StoreFormatter {

    public int strToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorCode.INVALID_NUMBER_FORMAT.getMessage());
        }
    }

    public LocalDate strToLocalDate(String str) {
        try {
            return LocalDate.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch(DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorCode.INVALID_DATE_FORMAT.getMessage());
        }
    }
}
