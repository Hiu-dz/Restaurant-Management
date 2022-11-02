package models;

import com.google.gson.annotations.JsonAdapter;

public class MenuModel {
    private String name;
    private String description;
    private double price;
    private TypeMenu type;

    public MenuModel() {
        this.name = "";
        this.description = "";
        this.price = 0;
        this.type = null;
    }

    public MenuModel(String name, String description, double price, TypeMenu type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TypeMenu getType() {
        return type;
    }

    public void setType(TypeMenu type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MenuModel{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }
}
