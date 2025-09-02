package store.app;

import java.util.List;
import java.util.Map;
import store.service.ProductManager;
import store.service.PromotionManager;
import store.util.StoreFileReader;
import store.util.StoreMapper;
import store.util.StoreParser;

public class ProductManagerFactory {

    private final StoreFileReader fileReader;
    private final StoreParser parser;
    private final StoreMapper mapper;

    public ProductManagerFactory(
            StoreFileReader fileReader,
            StoreParser storeParser,
            StoreMapper storeMapper
    ) {
        this.fileReader = fileReader;
        this.parser = storeParser;
        this.mapper = storeMapper;
    }

    public ProductManager createProductManager() {
        ProductManager productManager = new ProductManager();
        PromotionManager promotionManager = createPromotionManager();

        List<String> productRows = fileReader.readProducts();
        List<Map<String, String>> productInfos = parser.parseToMapList(productRows);
        productInfos.forEach(info -> productManager.save(
                mapper.toProduct(info, promotionManager.getByName(info.get("promotion"))
                )
        ));

        return productManager;
    }

    private PromotionManager createPromotionManager() {
        PromotionManager promotionManager = new PromotionManager();
        List<String> promotionRows = fileReader.readPromotions();
        List<Map<String, String>> promotionInfos = parser.parseToMapList(promotionRows);
        promotionInfos.forEach(info ->
                promotionManager.save(mapper.toPromotion(info))
        );
        return promotionManager;
    }

}
