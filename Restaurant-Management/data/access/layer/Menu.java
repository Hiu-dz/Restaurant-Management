package data.access.layer;

public class Menu {
    private String name;
    private String description;
    private double price;

    public Menu() {
        this.name = "";
        this.description = "";
        this.price = 0;
    }

    public Menu(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
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

    @Override
    public String toString() {
        return name + ", " + description + ", " + price;
    }
}
