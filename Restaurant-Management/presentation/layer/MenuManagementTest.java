package presentation.layer;

import business.logic.layer.MenuBusiness;

public class MenuManagementTest {
    public static void main(String[] args) {
        MenuBusiness menuBusiness = new MenuBusiness();

        menuBusiness.showManagement();
        menuBusiness.chooseFunction();
    }
}
