package store.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreParser {

    public List<Map<String, String>> parseToMapList(List<String> rows) {
        List<Map<String, String>> mapList = new ArrayList<>();
        rows.subList(1, rows.size()).forEach(row ->
                mapList.add(parseToMap(splitByComma(rows.getFirst()), splitByComma(row))));
        return mapList;
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

}
