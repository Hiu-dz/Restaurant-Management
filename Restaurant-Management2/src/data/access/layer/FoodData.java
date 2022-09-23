package data.access.layer;

import java.util.ArrayList;
import java.util.List;

public class FoodData extends MenuData {
    private static final List<FoodData> FOOD_DATA_LIST = new ArrayList<>();

    public FoodData() {
        super();
    }

    public FoodData(String name, String description, double price, String type) {
        super(name, description, price, type);
    }

    public static List<FoodData> getFoodDataList() {
        return FOOD_DATA_LIST;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
