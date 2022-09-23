package data.access.layer;

import java.util.ArrayList;
import java.util.List;

public class DrinkData extends MenuData {
    private static final List<DrinkData> DRINK_DATA_LIST = new ArrayList<>();

    public DrinkData() {
        super();
    }

    public DrinkData(String name, String description, double price, String type) {
        super(name, description, price, type);
    }

    public static List<DrinkData> getDrinkDataList() {
        return DRINK_DATA_LIST;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
