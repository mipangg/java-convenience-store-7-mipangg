package store.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.exception.ErrorCode;

public class StoreFileReader {

    public List<String> readProducts() {
        String productsPath = "src/main/resources/products.md";
        return convertMarkdownToStringList(productsPath);
    }

    public List<String> readPromotions() {
        String promotionPath = "src/main/resources/promotions.md";
        return convertMarkdownToStringList(promotionPath);
    }

    private List<String> convertMarkdownToStringList(String path) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorCode.FILE_NOT_FOUND.getMessage());
        }
        return lines;
    }

}
