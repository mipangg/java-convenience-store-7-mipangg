package store.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.exception.ErrorCode;

public class StoreFileReader {
    private final String productFilePath = "src/main/resources/products.md";
    private final String promotionFilePath = "src/main/resources/promotions.md";

    public List<String> getProducts() {
        return readFile(productFilePath);
    }

    public List<String> getPromotions() {
        return readFile(promotionFilePath);
    }

    private List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println(ErrorCode.FILE_NOT_FOUND.getMessage());
        }
        return lines;
    }
}
