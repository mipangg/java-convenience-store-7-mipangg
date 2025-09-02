package store.app;

import store.service.ProductManager;

public class StoreManager {

    private final ProductManager productManager;

    StoreManager(ProductManager productManager) {
        this.productManager = productManager;
    }

}
