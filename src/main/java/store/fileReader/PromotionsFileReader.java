package store.fileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import store.model.Promotion;

public class PromotionsFileReader {
    private final List<Promotion> promotions = new ArrayList<>();

    public PromotionsFileReader() {
        String productsFilePath = "src/main/resources/promotions.md";
        try (BufferedReader reader = new BufferedReader(new FileReader(productsFilePath))) {
            readPromotions(reader);
            reader.close();
        } catch (IOException e) {
            System.out.println("promotions.md 파일을 정상적으로 불러올 수 없습니다.");
        }
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    private void readPromotions(BufferedReader reader) throws IOException {
        String line = reader.readLine(); // header 제거
        while ((line = reader.readLine()) != null) {
            String[] elements = line.split(",");
            setPromotions(elements);
        }
    }

    private void setPromotions(String[] elements) {
        String name = elements[0];
        String buy = elements[1];
        String get = elements[2];
        String startDate = elements[3];
        String endDate = elements[4];
        promotions.add(new Promotion(name, buy, get, startDate, endDate));
    }
}
