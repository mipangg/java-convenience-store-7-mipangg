package store.app;

import java.io.FileNotFoundException;

public class Application {

    public static void main(String[] args) throws FileNotFoundException {
        Store store = new Store();
        store.run();
    }
}
