package data.access.layer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BillManagement {
    private String menuItem;
    private int quantity;
    private String orderedTime = this.getCurrentDateTime();
    private double priceItems;

    public BillManagement(String menuItem, int quantity, double priceItems) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.orderedTime = this.getCurrentDateTime();
        this.priceItems = priceItems;
    }

    public String getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderedTime() {
        return orderedTime;
    }

    public double getPriceItems() {
        return priceItems;
    }

    public void setPriceItems(double priceItems) {
        this.priceItems = priceItems;
    }

    @Override
    public String toString() {
        return "BillManagement{" +
                "menuItem='" + menuItem + '\'' +
                ", quantity=" + quantity +
                ", orderedTime=" + orderedTime +
                ", priceItems=" + priceItems +
                '}';
    }

    private String getCurrentDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return localDateTime.format(dateTimeFormatter);
    }
}
