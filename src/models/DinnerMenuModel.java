package models;

public class DinnerMenuModel extends FoodMenuModel {
    public DinnerMenuModel() {
        super();
    }

    public DinnerMenuModel(String name, String description, double price, TypeMenu type) {
        super(name, description, price, type);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
