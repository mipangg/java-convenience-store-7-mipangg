package store.view;

import java.util.List;
import store.model.OutputModel;
import store.model.Product;

public class OutputView {
    OutputModel outputModel;

    public OutputView() {
        outputModel = new OutputModel();
    }

    public void printProducts() {
        printTitle();
        outputModel.getProducts().forEach(System.out::println);
    }

    public void printUpdateProducts() {
        printTitle();
    }

    public void printReceipt(String receipt) {
        System.out.println(receipt);
    }

    private void printTitle() {
        System.out.println("\n안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");
    }
}
