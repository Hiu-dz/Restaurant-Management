package models;

public class AlcoholMenuModel extends DrinkMenuModel {
    public AlcoholMenuModel() {
        super();
    }

    public AlcoholMenuModel(String name, String description, double price, TypeMenu type) {
        super(name, description, price, type);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
