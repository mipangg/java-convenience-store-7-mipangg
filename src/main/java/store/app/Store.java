package store.app;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.Promotion;
import store.mapper.StoreMapper;
import store.service.ProductManager;
import store.service.PromotionManager;
import store.util.StoreFileReader;
import store.util.StoreParser;
import store.view.StoreView;

public class Store {

    private final StoreFileReader fileReader;
    private final StoreParser storeParser;
    private final StoreMapper storeMapper;

    private final StoreView storeView;

    private final ProductManager productManager;
    private final PromotionManager promotionManager;

    public Store() {
        fileReader = new StoreFileReader();
        storeParser = new StoreParser();
        storeMapper = new StoreMapper();
        storeView = new StoreView();
        productManager = new ProductManager();
        promotionManager = new PromotionManager();
    }

    public void run() throws FileNotFoundException {
        setPromotions();
        setProducts();
        storeView.printProductList(productManager.findAll());
    }

    private void setPromotions() throws FileNotFoundException {
        List<String> promotionInfos = fileReader.readPromotions();
        List<Map<String, String>> parsedPromotionInfos = storeParser.getParsedInfos(promotionInfos);
        for (Map<String, String> parsedPromotionInfo : parsedPromotionInfos) {
            promotionManager.save(storeMapper.toPromotion(parsedPromotionInfo));
        }
    }

    private void setProducts() throws FileNotFoundException {
        List<String> productInfos = fileReader.readProducts();
        List<Map<String, String>> parsedProductInfos = storeParser.getParsedInfos(productInfos);
        for (Map<String, String> productInfo : parsedProductInfos) {
            Promotion promotion = promotionManager.findByName(productInfo.get("promotion"));
            productManager.save(storeMapper.toProduct(productInfo, promotion));
        }
        productManager.manageInventory();
    }

}
