package models;

public class LunchMenuModel extends FoodMenuModel {
    public LunchMenuModel() {
        super();
    }

    public LunchMenuModel(String name, String description, double price, TypeMenu type) {
        super(name, description, price, type);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
