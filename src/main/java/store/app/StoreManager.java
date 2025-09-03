package store.app;

import store.service.ProductManager;
import store.util.StoreMapper;
import store.util.StoreParser;
import store.view.InputView;
import store.view.OutputView;

public class StoreManager {

    private final ProductManager productManager;
    private final StoreParser parser;
    private final StoreMapper mapper;
    private final InputView inputView;
    private final OutputView outputView;

    public StoreManager(
            ProductManager productManager,
            StoreParser parser,
            StoreMapper mapper,
            InputView inputView,
            OutputView outputView
    ) {
        this.productManager = productManager;
        this.parser = parser;
        this.mapper = mapper;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printProducts(productManager.findAll());
        String order = getOrder();
    }

    private String getOrder() {
        try {
            return inputView.askOrder();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return getOrder();
        }
    }

}
