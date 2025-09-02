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

        getProductInfos().forEach(info -> productManager.save(
                mapper.toProduct(info, promotionManager.getByName(info.get("promotion"))
                )
        ));
        productManager.updatePromotionProducts();
        productManager.createRegularProductIfAbsent();
        return productManager;
    }

    private PromotionManager createPromotionManager() {
        PromotionManager promotionManager = new PromotionManager();
        getPromotionInfos().forEach(info ->
                promotionManager.save(mapper.toPromotion(info))
        );
        return promotionManager;
    }

    private List<Map<String, String>> getPromotionInfos() {
        List<String> promotionRows = fileReader.readPromotions();
        return parser.parseToMapList(promotionRows);
    }

    private List<Map<String, String>> getProductInfos() {
        List<String> productRows = fileReader.readProducts();
        return parser.parseToMapList(productRows);
    }
}
