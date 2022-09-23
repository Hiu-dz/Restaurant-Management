package presentation.layer;

import business.logic.layer.MenuBusiness;

public class MenuTest {
    public static void main(String[] args) {
        System.out.println("####----- Welcome to Hiu 2nd Restaurant -----####");

        MenuBusiness menuBusiness = new MenuBusiness();

        menuBusiness.showMenu();
        menuBusiness.showChooseItem();
    }
}