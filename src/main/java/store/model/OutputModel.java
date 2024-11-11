package store.model;

import java.util.List;
import store.model.Inventory;

public class OutputModel {
    public List<Product> getProducts() {
        Inventory inventory = new Inventory();
        return inventory.getTotalProducts();
    }
}
