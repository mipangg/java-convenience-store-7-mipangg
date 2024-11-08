package store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductsFileReader {
    final String productsFilePath = "src/main/resources/products.md";
    List<Product> products = new ArrayList<>();

    public ProductsFileReader() {
        try (BufferedReader reader = new BufferedReader(new FileReader(productsFilePath))) {
            readProducts(reader);
        } catch (IOException e) {
            System.out.println("products.md 파일을 정상적으로 불러올 수 없습니다.");
        }
    }

    private void readProducts(BufferedReader reader) throws IOException {
        String line = reader.readLine(); // header 제거
        while ((line = reader.readLine()) != null) {
            String[] elements = line.split(",");
            setProducts(elements);
        }
    }

    private void setProducts(String[] elements) {
        String name = elements[0];
        String price = elements[1];
        String quantity = elements[2];
        String promotion = elements[3];
        products.add(new Product(name, price, quantity, promotion));
    }

    public List<Product> getProducts() {
        return products;
    }
}
