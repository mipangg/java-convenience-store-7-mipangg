package store.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.dto.ShoppingItem;

public class StoreParser {

    public List<ShoppingItem> getShoppingItems(String shoppinglistStr) {
        List<ShoppingItem> shoppingItems = new ArrayList<>();

        String[] shoppingInfos = splitByComma(shoppinglistStr);
        for (String shoppingInfo : shoppingInfos) {
            shoppingItems.add(getShoppingItem(shoppingInfo));
        }

        return shoppingItems;
    }

    public List<Map<String, String>> parseInfos(List<String> strs) {
        String[] titles = splitByComma(strs.getFirst());
        List<String[]> contents = getContents(strs.subList(1, strs.size()));
        return getInfos(titles, contents);
    }

    private ShoppingItem getShoppingItem(String shoppingInfo) {
        shoppingInfo = trimShoppingInfo(shoppingInfo);
        String[] item = splitByDash(shoppingInfo);
        return new ShoppingItem(item[0], Integer.parseInt(item[1]));
    }

    private String trimShoppingInfo(String shoppingInfo) {
        return shoppingInfo.replace("[", "")
                .replace("]", "")
                .trim();
    }

    // 여러 상품이 각각의 Map형태로 저장되어 반환
    private List<Map<String, String>> getInfos(String[] titles, List<String[]> contents) {
        List<Map<String, String>> infos = new ArrayList<>();
        for (String[] content : contents) {
            infos.add(getInfo(titles, content));
        }
        return infos;
    }

    // 1개의 상품에 대해 <헤더, 내용>으로 반환
    private Map<String, String> getInfo(String[] titles, String[] contents) {
        Map<String, String> info = new HashMap<>();
        for (int i = 0; i < titles.length; i++) {
            info.put(titles[i], contents[i]);
        }
        return info;
    }

    // 헤더를 제외한 내용을 분리하여 리스트로 반환
    private List<String[]> getContents(List<String> productStrs) {
        List<String[]> contents = new ArrayList<>();
        for (String productStr : productStrs) {
            String[] content = splitByComma(productStr);
            contents.add(content);
        }
        return contents;
    }

    // ,를 기준으로 단어 분리
    private String[] splitByComma(String str) {
        return str.split(",");
    }

    // -를 기준으로 단어 분리
    private String[] splitByDash(String str) {
        return str.split("-");
    }

}
