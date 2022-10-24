package views;

import controllers.BillController;
import controllers.MenuController;
import models.BillModel;
import models.MenuModel;
import models.TypeMenu;
import repositories.BillRepository;
import repositories.MenuRepository;
import services.BillService;
import services.MenuService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        MenuView menuView = new MenuView();
        BillRepository billRepository = new BillRepository();
        BillView billView = new BillView();
        MenuController menuController = new MenuController();
        BillController billController = new BillController();
        BillService billService = new BillService();
        MenuService menuService = new MenuService();
        MenuRepository menuRepository = new MenuRepository();

//        menuView.showMenu();
        /*MenuModel menuModel1 = new MenuModel("name1", "des1", 1, TypeMenu.ALCOHOL);
        MenuModel menuModel2 = new MenuModel("name2", "des2", 2, TypeMenu.SOFT_DRINK);
        MenuModel menuModel3 = new MenuModel("name3", "des3", 3, TypeMenu.BREAKFAST);
        MenuModel menuModel4 = new MenuModel("name4", "des4", 4, TypeMenu.DINNER);
        Map<MenuModel, Integer> map = new HashMap<>();
        map.put(menuModel1, 1);
        map.put(menuModel2, 2);
        map.put(menuModel3, 3);
        map.put(menuModel4, 4);

        int i = 1;
        for (Integer m : map.values()) {
            System.out.println(i++ +". "+ m);
        }*/


        /*List<BillModel> bills = new ArrayList<>();
        int i = 0;
        for (Map.Entry<MenuModel, Integer> m : map.entrySet()){
            Map<MenuModel, Integer> map1 = new HashMap<>(1);
            map1.put(m.getKey(), m.getValue());
            bills.add(new BillModel(map1, LocalDate.now()));
            System.out.println(bills.get(i++));
        }*/

//        menuView.showManagement();

//        billRepository.getAllDateBill().forEach(System.out::println);
//        billRepository.getAllBill("09-10-2022").forEach(System.out::println);
//        billView.showBillManagement();
//        billRepository.createBill("11-10-2022");
//        System.out.println(menuService.getOrderedTime())

        /*List<String> list = Arrays.asList("");

        // get last element from a list
        String result = list.get(list.size() - 1);
        System.out.println(result);*/
//        System.out.println(TypeMenu.SOFT_DRINK.getFile());

       /* for (TypeMenu t : TypeMenu.values()) {
            System.out.println(t);
        }*/

    }
}
