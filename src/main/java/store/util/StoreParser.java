package store.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreParser {

    public List<Map<String, String>> getParsedInfos(List<String> infos) {
        List<Map<String, String>> parsedInfos = new ArrayList<>();

        String[] headers = splitByComma(infos.getFirst());

        List<String> contents = infos.subList(1, infos.size());
        for (String content : contents) {
            parsedInfos.add(getParsedInfo(headers, splitByComma(content)));
        }

        return parsedInfos;
    }

    // 콤마로 분리된 header와 contents 한 줄을 map으로 반환
    private Map<String, String> getParsedInfo(String[] headers, String[] contents) {
        Map<String, String> parsedInfo = new HashMap<>();
        for (int i = 0; i < contents.length; i++) {
            parsedInfo.put(headers[i], contents[i]);
        }
        return parsedInfo;
    }

    // 콤마를 기준으로 문자열 분리
    private String[] splitByComma(String line) {
        return line.split(",");
    }

}
