package store.model;

import java.util.Arrays;
import java.util.List;
import store.Error;
import store.view.InputView;

public class InputModel {
    static final String YES = "Y";
    static final String NO = "N";
    static final boolean GO = true;
    static final boolean STOP = false;

    InputView inputView;

    public InputModel() {
        inputView = new InputView();
    }

    public List<String> getUserOrders(String userOrderText) {
        List<String> userOrders = Arrays.stream(userOrderText.split(","))
                .map(text -> text.replace("[", "").replace("]", ""))
                .toList();
        return userOrders;
    }

    public boolean wantMembershipDiscount() {
        String userInput = inputView.inputWantMembership();
        userInput = userInput.replace(" ", "");
        if (userInput.equals(YES)) { return GO; }
        if (userInput.equals(NO)) { return STOP; }
        throw new IllegalArgumentException(Error.INCORRECT_INPUT.getMessage());
    }

    public boolean wantToBuyRegularPrice(String product, int quantity) {
        String userInput = inputView.inputWantToBuyRegularPrice(product, quantity);
        userInput = userInput.replace(" ", "");
        if (userInput.equals(YES)) { return GO; }
        if (userInput.equals(NO)) { return STOP; }
        throw new IllegalArgumentException(Error.INCORRECT_INPUT.getMessage());
    }

    public boolean wantToAddMoreProduct(String product) {
        String userInput = inputView.inputWantToAddMoreProduct(product);
        userInput = userInput.replace(" ", "");
        if (userInput.equals(YES)) { return GO; }
        if (userInput.equals(NO)) { return STOP; }
        throw new IllegalArgumentException(Error.INCORRECT_INPUT.getMessage());
    }

    public boolean wantMoreShopping() {
        String userInput = inputView.inputMoreShopping();
        userInput = userInput.replace(" ", "");
        if (userInput.equals(YES)) { return GO; }
        if (userInput.equals(NO)) { return STOP; }
        throw new IllegalArgumentException(Error.INCORRECT_INPUT.getMessage());
    }
}
