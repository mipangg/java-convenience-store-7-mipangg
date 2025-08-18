package store.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import store.exception.ErrorCode;

public class StoreFormatter {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public int strToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorCode.INVALID_FORMAT.getMessage());
        }
    }

    public LocalDate strToLocalDate(String str) {
        try {
            return LocalDate.parse(str, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorCode.INVALID_FORMAT.getMessage());
        }
    }

}
