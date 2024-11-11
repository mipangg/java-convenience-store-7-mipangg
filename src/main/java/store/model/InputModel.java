package store.model;

import java.util.Arrays;
import java.util.List;
import store.Error;
import store.Validator;
import store.view.InputView;

public class InputModel {
    InputView inputView;
    Validator validator;

    public InputModel() {
        inputView = new InputView();
        validator = new Validator();
    }

    public List<String> getUserOrders(String userOrderText) {
        List<String> userOrders = Arrays.stream(userOrderText.split(","))
                .map(text -> text.replace("[", "").replace("]", ""))
                .toList();
        return userOrders;
    }

    public boolean wantMembershipDiscount() {
        String userInput = inputView.inputWantMembership();
        try {
            return validator.validateYesOrNo(userInput);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return wantMembershipDiscount();
        }
    }

    public boolean wantToBuyRegularPrice(String product, int quantity) {
        String userInput = inputView.inputWantToBuyRegularPrice(product, quantity);
        try {
            return validator.validateYesOrNo(userInput);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return wantToBuyRegularPrice(product, quantity);
        }
    }

    public boolean wantToAddMoreProduct(String product) {
        String userInput = inputView.inputWantToAddMoreProduct(product);
        try {
            return validator.validateYesOrNo(userInput);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return wantToAddMoreProduct(product);
        }
    }

    public boolean wantMoreShopping() {
        String userInput = inputView.inputMoreShopping();
        try {
            return validator.validateYesOrNo(userInput);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return wantMoreShopping();
        }
    }
}
