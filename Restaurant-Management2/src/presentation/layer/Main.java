package presentation.layer;

import business.logic.layer.MenuBusiness;
import data.access.layer.DrinkData;

public class Main {
    public static void main(String[] args) {
        MenuBusiness menuBusiness = new MenuBusiness();
        menuBusiness.showMenu();
    }
}
