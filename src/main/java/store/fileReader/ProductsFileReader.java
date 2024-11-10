package store.fileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.model.Product;

public class ProductsFileReader {
    private final List<Product> products = new ArrayList<>();
    private Product prevProduct;

    public ProductsFileReader() {
        String productsFilePath = "src/main/resources/products.md";
        initPrevProduct();
        try (BufferedReader reader = new BufferedReader(new FileReader(productsFilePath))) {
            readProducts(reader);
            reader.close();
        } catch (IOException e) {
            System.out.println("products.md 파일을 정상적으로 불러올 수 없습니다.");
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    private void readProducts(BufferedReader reader) throws IOException {
        String line = reader.readLine(); // header 제거
        while ((line = reader.readLine()) != null) {
            String[] elements = line.split(",");
            setProducts(elements);
        }
    }

    private void setProducts(String[] elements) {
        if (! prevProduct.getPromotion().equals("null")) {
            checkRegularProduct(elements, prevProduct);
        }
        products.add(new Product(elements[0], elements[1], elements[2], elements[3]));
        prevProduct = products.getLast();
    }

    private void checkRegularProduct(String[] elements, Product prevProduct) {
        if (! elements[0].equals(prevProduct.getName())) {
            String prevName = prevProduct.getName();
            String prevPrice = Integer.toString(prevProduct.getPrice());
            products.add(new Product(prevName, prevPrice, "0", "null"));
        }
    }

    private void initPrevProduct() {
        prevProduct = new Product("name", "0", "0", "null");
    }


    public List<String> getProductsInfo() {
        List<String> productsInfo = new ArrayList<>();
        for (Product product : products) {
            productsInfo.add(product.toString());
        }
        return productsInfo;
    }
}
