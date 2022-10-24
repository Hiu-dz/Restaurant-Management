package models;

import java.time.LocalDate;
import java.util.Map;

public class BillModel {
    private Map<MenuModel, Integer> menuItems;
    private LocalDate orderedTime;

    public BillModel() {
        this.menuItems = null;
        this.orderedTime = null;
    }

    public BillModel(Map<MenuModel, Integer> menuItem, LocalDate orderedTime) {
        this.menuItems = menuItem;
        this.orderedTime = orderedTime;
    }

    public Map<MenuModel, Integer> getMenuItem() {
        return menuItems;
    }

    public void setMenuItem(Map<MenuModel, Integer> menuItems) {
        this.menuItems = menuItems;
    }

    public LocalDate getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalDate orderedTime) {
        this.orderedTime = orderedTime;
    }

    @Override
    public String toString() {
        return "BillModel{" +
                "menuItem=" + menuItems +
                ", orderedTime=" + orderedTime +
                '}';
    }
}
