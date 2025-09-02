package store.app;

import store.util.StoreFileReader;
import store.util.StoreMapper;
import store.util.StoreParser;

public class Application {

    public static void main(String[] args) {

        StoreFileReader fileReader = new StoreFileReader();
        StoreParser parser = new StoreParser();
        StoreMapper mapper = new StoreMapper();

        ProductManagerFactory productManagerFactory = new ProductManagerFactory(
                fileReader,
                parser,
                mapper
        );

        StoreManager storeManager = new StoreManager(
                productManagerFactory.createProductManager(),
                fileReader,
                parser,
                mapper
        );
        storeManager.run();
    }
}
