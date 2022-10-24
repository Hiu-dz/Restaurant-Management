package models;

public class BreakfastMenuModel extends FoodMenuModel {
    public BreakfastMenuModel() {
        super();
    }

    public BreakfastMenuModel(String name, String description, double price, TypeMenu type) {
        super(name, description, price, type);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
