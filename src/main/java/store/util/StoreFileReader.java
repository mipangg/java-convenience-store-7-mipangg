package store.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.exception.ErrorCode;

public class StoreFileReader {
    private final String PRODUCTS_PATH = "src/main/resources/products.md";
    private final String PROMOTIONS_PATH = "src/main/resources/promotions.md";

    public List<String> readProducts() throws FileNotFoundException {
        return readFile(PRODUCTS_PATH);
    }

    public List<String> readPromotions() throws FileNotFoundException {
        return readFile(PROMOTIONS_PATH);
    }

    private List<String> readFile(String path) throws FileNotFoundException {
        List<String> fileLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileLines.add(line);
            }
        } catch (IOException e) {
            throw new FileNotFoundException(ErrorCode.FILE_NOT_FOUND.getMessage());
        }

        return fileLines;
    }

}
