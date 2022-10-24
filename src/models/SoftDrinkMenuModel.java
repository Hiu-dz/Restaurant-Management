package models;

public class SoftDrinkMenuModel extends DrinkMenuModel {
    public SoftDrinkMenuModel() {
        super();
    }

    public SoftDrinkMenuModel(String name, String description, double price, TypeMenu type) {
        super(name, description, price, type);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
