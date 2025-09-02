package store.app;

import store.service.ProductManager;
import store.util.StoreFileReader;
import store.util.StoreMapper;
import store.util.StoreParser;

public class StoreManager {

    private final ProductManager productManager;
    private final StoreFileReader fileReader;
    private final StoreParser parser;
    private final StoreMapper mapper;

    public StoreManager(
            ProductManager productManager,
            StoreFileReader fileReader,
            StoreParser parser,
            StoreMapper mapper
    ) {
        this.productManager = productManager;
        this.fileReader = fileReader;
        this.parser = parser;
        this.mapper = mapper;
    }

    public void run() {

    }

}
