package store.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.dto.OrderItemRequest;
import store.exception.ErrorCode;

public class StoreParser {

    // .md 파일로 부터 읽어들인 정보를 저장한 List<String>을 List<Map>으로 변환
    public List<Map<String, String>> parseToMapList(List<String> rows) {
        List<Map<String, String>> mapList = new ArrayList<>();
        rows.subList(1, rows.size()).forEach(row ->
                mapList.add(parseToMap(splitByComma(rows.getFirst()), splitByComma(row))));
        return mapList;
    }

    // 입력된 주문 상품 정보를 OrderItemRequest로 반환
    public List<OrderItemRequest> parseToOrderItemRequestList(String row) {
        List<OrderItemRequest> orderItemRequests = new ArrayList<>();
        for (String str : splitByComma(row)) {
            orderItemRequests.add(
                    getOrderItemRequest(
                            splitByHyphen(validateBracketFormat(str.trim()))
                    )
            );
        }
        return orderItemRequests;
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

    private OrderItemRequest getOrderItemRequest(String[] infos) {
        try {
            return new OrderItemRequest(infos[0], Integer.parseInt(infos[1]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorCode.INVALID_FORMAT.getMessage());
        }
    }

}
