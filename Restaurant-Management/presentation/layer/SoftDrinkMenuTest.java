package presentation.layer;

import business.logic.layer.BillManagementBusiness;
import business.logic.layer.SoftDinkBusiness;

public class SoftDrinkMenuTest {
    public static void main(String[] args) {
        SoftDinkBusiness softDinkBusiness = new SoftDinkBusiness();
        BillManagementBusiness billManagementBusiness = new BillManagementBusiness();

        /*softDinkBusiness.displaySoftDrinkMenu();

        softDinkBusiness.addSoftDrink();
        softDinkBusiness.displaySoftDrinkMenu();

        softDinkBusiness.updateSoftDrink();
        softDinkBusiness.displaySoftDrinkMenu();

        softDinkBusiness.deleteSoftDrink();
        softDinkBusiness.displaySoftDrinkMenu();

        softDinkBusiness.showManagement();
        softDinkBusiness.chooseFunction();

        softDinkBusiness.getSoftDinkBusinesses().forEach(System.out::println);*/

        softDinkBusiness.showMenu();
        softDinkBusiness.showChooseItem();
//        billManagementBusiness.showBill();

        /*softDinkBusiness.showMenu();
        softDinkBusiness.updateItem();
        softDinkBusiness.showMenu();*/


        billManagementBusiness.exportBill();
    }
}