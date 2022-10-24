package models;

public class DrinkMenuModel extends MenuModel {
    public DrinkMenuModel() {
        super();
    }

    public DrinkMenuModel(String name, String description, double price, TypeMenu type) {
        super(name, description, price, type);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
