package models;

public class FoodMenuModel extends MenuModel {
    public FoodMenuModel() {
        super();
    }

    public FoodMenuModel(String name, String description, double price, TypeMenu type) {
        super(name, description, price, type);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
