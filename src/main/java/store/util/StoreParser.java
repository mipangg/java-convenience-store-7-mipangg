package store.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.exception.ErrorCode;

public class StoreParser {

    // .md 파일로 부터 읽어들인 정보를 저장한 List<String>을 List<Map>으로 변환
    public List<Map<String, String>> parseToMapList(List<String> rows) {
        List<Map<String, String>> mapList = new ArrayList<>();
        rows.subList(1, rows.size()).forEach(row ->
                mapList.add(parseToMap(splitByComma(rows.getFirst()), splitByComma(row))));
        return mapList;
    }

    // 입력된 주문 상품 정보를 Map으로 반환
    public Map<String, String> parseToMap(String row) {
        Map<String, String> parsedMap = new LinkedHashMap<>();
        String[] splitByCommaStrs = splitByComma(row);
        for (String str : splitByCommaStrs) {
            String[] splitByHyphenStrs = splitByHyphen(validateBracketFormat(str.trim()));
            parsedMap.put(splitByHyphenStrs[0], splitByHyphenStrs[1]);
        }
        return parsedMap;
    }

    private Map<String, String> parseToMap(String[] headers, String[] contents) {
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < headers.length; i++) {
            map.put(headers[i].trim(), contents[i].trim());
        }

        return map;
    }

    private String[] splitByComma(String line) {
        return line.split(",");
    }

    private String[] splitByHyphen(String line) {
        return line.split("-");
    }

    private String validateBracketFormat(String item) {
        if (!isEnclosedInBrackets(item)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_FORMAT.getMessage());
        }
        return item.replace("[", "").replace("]", "");
    }

    private boolean isEnclosedInBrackets(String item) {
        return item.startsWith("[") && item.endsWith("]") &&
                !item.substring(1, item.length() - 1).contains("[") &&
                !item.substring(1, item.length() - 1).contains("]");
    }

}
