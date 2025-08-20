package store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.domain.Order;
import store.domain.OrderItem;
import store.domain.Product;
import store.domain.Promotion;
import store.mapper.StoreMapper;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.util.StoreFileReader;
import store.util.StoreFormatter;
import store.util.StoreParser;
import store.view.InputView;
import store.view.OutputView;

public class Store {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final StoreFileReader fileReader = new StoreFileReader();
    private final StoreParser storeParser = new StoreParser();
    private final StoreMapper storeMapper = new StoreMapper();
    private final StoreFormatter storeFormatter = new StoreFormatter();

    private PromotionRepository promotionRepository;
    private ProductRepository productRepository;
    private Order order;

    public Store() {
        promotionRepository = new PromotionRepository(setPromotions());
        productRepository = new ProductRepository(storeFormatter.listToMap(setProducts()));
    }

    public void run() {
        outputView.showInventory(productRepository.getProducts());
    }

    private List<Promotion> setPromotions() {
        List<String> promotionStrs = fileReader.getPromotionStrs();
        List<Map<String, String>> promotionInfos = storeParser.parseInfos(promotionStrs);

        List<Promotion> promotions = new ArrayList<>();
        for (Map<String, String> promotionInfo : promotionInfos) {
            promotions.add(storeMapper.toPromotion(promotionInfo));
        }
        return promotions;
    }

    private List<Product> setProducts() {
        List<String> productStrs = fileReader.getProductStrs();
        List<Map<String, String>> productInfos = storeParser.parseInfos(productStrs);

        List<Product> products = new ArrayList<>();
        for (Map<String, String> productInfo : productInfos) {
            products.add(
                    storeMapper.toProduct(
                            productInfo,
                            promotionRepository.getPromotionByName(productInfo.get("promotion"))
                    )
            );
        }
        return products;
    }

}
