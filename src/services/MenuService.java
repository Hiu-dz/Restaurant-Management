package services;

import models.BillModel;
import models.MenuModel;
import models.TypeMenu;
import repositories.MenuRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuService {
    private final MenuRepository menuRepository;
    private final BillService billService;

    public MenuService() {
        this.menuRepository = new MenuRepository();
        this.billService = new BillService();
    }

    /**
     * Check item name has been existed
     *
     * @param name: item name
     * @param type: type menu
     */
    public boolean hasItemName(String name, TypeMenu type) {
        String fileName = TypeMenu.getFileByType(type);
        return this.menuRepository.hasItemName(fileName, name);
    }

    /**
     * Handle add menu item at bill list
     *
     * @param typeMenu:   drink menu or food menu
     * @param itemNumber: item number
     * @param quantity:   quantity of item
     */
    public void orderMenuItem(String typeMenu, int itemNumber, int quantity) {
        List<MenuModel> menus = this.menuRepository.getAllMenuItems();
        List<MenuModel> drinkMenus = menus
                .stream()
                .filter(m -> m.getType() == TypeMenu.SOFT_DRINK || m.getType() == TypeMenu.ALCOHOL)
                .toList();
        List<MenuModel> foodMenus = menus
                .stream()
                .filter(m -> m.getType() == TypeMenu.BREAKFAST || m.getType() == TypeMenu.LUNCH || m.getType() == TypeMenu.DINNER)
                .toList();
        List<MenuModel> tempMenu = new ArrayList<>();
        String itemName = null;
        String itemDescription = null;
        double itemPrice = 0;
        TypeMenu type = null;

        if (typeMenu.equals("DrinkMenu")) {
            tempMenu = drinkMenus.stream().toList();
        } else if (typeMenu.equals("FoodMenu")) {
            tempMenu = foodMenus.stream().toList();
        }

        for (int i = 0; i < tempMenu.size(); i++) {
            int locationItem = i + 1;
            if (itemNumber == locationItem) {
                itemName = tempMenu.get(i).getName();
                itemDescription = tempMenu.get(i).getDescription();
                itemPrice = tempMenu.get(i).getPrice();
                type = tempMenu.get(i).getType();
                break;
            }
        }

        MenuModel menuItem = new MenuModel(itemName, itemDescription, itemPrice, type);
        this.billService.addMenuItem(menuItem, quantity);
    }

    /**
     * Create bill and display bill on console
     */
    public void exportBill() {
        this.billService.createBill();
        this.billService.printBillWhenOrderDone();
    }

    /**
     * Add item at menu
     *
     * @param menuModel: object menu model
     */
    public boolean addItem(MenuModel menuModel) {
        String fileName = TypeMenu.getFileByType(menuModel.getType());
        if (!this.menuRepository.hasItemName(fileName, menuModel.getName())) {
            this.menuRepository.addItem(fileName, menuModel);
            System.out.println("--> ALERT: New item has been added");
            return true;
        } else {
            System.out.println("--> ERROR: Something went wrong");
            return false;
        }
    }

    /**
     * Get menu list from menu file
     *
     * @param type: type menu
     * @return menu list
     */
    public List<MenuModel> getMenuList(TypeMenu type) {
        String fileName = TypeMenu.getFileByType(type);
        return this.menuRepository.getMenuList(fileName);
    }

    /**
     * Get menu list from menu file
     *
     * @param fileName: file name
     * @return list of menu
     */
    public List<MenuModel> getMenuList(String fileName) {
        return this.menuRepository.getMenuList(fileName);
    }

    /**
     * Update item in menu
     *
     * @param nameWantUpdate: item name want update
     * @param menuItem:       object of menu
     * @return true if update successfully
     */
    public boolean isUpdateItem(String nameWantUpdate, MenuModel menuItem) {
        String fileName = TypeMenu.getFileByType(menuItem.getType());
        List<MenuModel> menuList = this.menuRepository.getMenuList(fileName);
        for (MenuModel m : menuList) {
            if (m.getName().equals(nameWantUpdate)) {
                String name = menuItem.getName().equals("n") ? m.getName() : menuItem.getName();
                String description = menuItem.getDescription().equals("n") ? m.getDescription() : menuItem.getDescription();
                double price = menuItem.getPrice() == 0 ? m.getPrice() : menuItem.getPrice();
                MenuModel menuModel = new MenuModel(name, description, price, menuItem.getType());

                this.menuRepository.updateItem(fileName, nameWantUpdate, menuModel);

                System.out.println("--> ALERT: Item has been updated");
                return true;
            }
        }
        System.out.println("--> WARNING: Something went wrong");
        return false;
    }

    /**
     * Remove item in menu
     *
     * @param name: item name
     * @param type: type menu
     */
    public void removeItem(String name, TypeMenu type) {
        String fileName = TypeMenu.getFileByType(type);
        this.menuRepository.removeItem(fileName, name);
        System.out.println("--> ALERT: Item has been removed");
    }
}
