package store.util;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.dto.OrderItemRequest;

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

    // 구매 상품 변환
    public List<OrderItemRequest> getOrderItemRequests(String order) {
        List<OrderItemRequest> orderItemRequests = new ArrayList<>();

        String[] orderItemInfos = splitByComma(order);
        for (String orderItemInfo : orderItemInfos) {
            orderItemRequests.add(getOrderItemRequest(orderItemInfo));
        }

        return orderItemRequests;
    }

    private OrderItemRequest getOrderItemRequest(String orderItemInfo) {
        String[] info = splitByDash(
                orderItemInfo
                        .replace("[", "")
                        .replace("]", "")
        );
        return OrderItemRequest.toOrderItemRequest(info);
    }

    // 콤마로 분리된 header와 contents 한 줄을 map으로 반환
    private Map<String, String> getParsedInfo(String[] headers, String[] contents) {
        Map<String, String> parsedInfo = new LinkedHashMap<>();
        for (int i = 0; i < contents.length; i++) {
            parsedInfo.put(headers[i], contents[i]);
        }
        return parsedInfo;
    }

    // 콤마를 기준으로 문자열 분리
    private String[] splitByComma(String line) {
        return line.split(",");
    }

    // -를 기준으로 문자열 분리
    private String[] splitByDash(String line) {
        return line.split("-");
    }

}
