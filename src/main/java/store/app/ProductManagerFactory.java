package store.app;

import java.util.List;
import java.util.Map;
import store.service.ProductManager;
import store.util.StoreFileReader;
import store.util.StoreMapper;
import store.util.StoreParser;

public class ProductManagerFactory {

    private final StoreFileReader storeFileReader;
    private final StoreParser storeParser;
    private final StoreMapper storeMapper;

    public ProductManagerFactory(
            StoreFileReader storeFileReader,
            StoreParser storeParser,
            StoreMapper storeMapper
    ) {
        this.storeFileReader = storeFileReader;
        this.storeParser = storeParser;
        this.storeMapper = storeMapper;
    }

    public ProductManager createProductManager() {
        ProductManager productManager = new ProductManager();

        List<String> promotionRows = storeFileReader.readPromotions();
        List<Map<String, String>> promotionInfos = storeParser.parseToMapList(promotionRows);
        for (Map<String, String> promotionInfo : promotionInfos) {

        }

        return productManager;
    }

}
