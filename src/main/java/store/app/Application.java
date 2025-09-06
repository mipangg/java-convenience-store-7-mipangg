package store.app;

import store.util.StoreFileReader;
import store.util.StoreMapper;
import store.util.StoreParser;
import store.view.InputView;
import store.view.OutputView;

public class Application {

    public static void main(String[] args) {

        StoreParser parser = new StoreParser();
        StoreMapper mapper = new StoreMapper();

        ProductManagerFactory productManagerFactory = new ProductManagerFactory(
                new StoreFileReader(),
                parser,
                mapper
        );

        StoreManager storeManager = new StoreManager(
                productManagerFactory.createProductManager(),
                parser,
                mapper,
                new InputView(),
                new OutputView()
        );
        storeManager.run();
    }
}
