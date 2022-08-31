package presentation.layer;

import business.logic.layer.MenuBusiness;
import business.logic.layer.SoftDinkBusiness;

public class MenuTest {
    public static void main(String[] args) {
        System.out.println("####----- Welcome to Hiu 2nd Restaurant ----####");

        MenuBusiness menuBusiness = new MenuBusiness();
        SoftDinkBusiness softDinkBusiness = new SoftDinkBusiness();

        softDinkBusiness.addItemToSoftDrink("aa", "bb", 1122);

        menuBusiness.displayMenu();
        menuBusiness.chooseMenu();
    }
}