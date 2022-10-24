package controllers;

import models.MenuModel;
import models.TypeMenu;
import services.MenuService;

import java.util.Collections;
import java.util.List;

public class MenuController {
    private final MenuService menuService;

    public MenuController() {
        this.menuService = new MenuService();
    }

    /**
     * Check string is integer
     *
     * @param str: string
     * @return true if string is integer
     */
    public boolean isPositiveInteger(String str) {
        if (str == null) {
            return false;
        }
        return str.matches("\\d+$");
    }

    /**
     * Check string is numeric
     *
     * @param str: string
     * @return true if string is numeric
     */
    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Validate input choose number of main menu
     *
     * @param chooseNumber: choose number of menu
     * @return true if valid choose number
     */
    public boolean isValidChooseMenu(String chooseNumber) {
        if (isPositiveInteger(chooseNumber)) {
            int menuNumber = Integer.parseInt(chooseNumber);
            int drinkMenu = 1;
            int foodMenu = 2;
            int exitProgram = 0;
            if (menuNumber == drinkMenu || menuNumber == foodMenu) {
                return true;
            } else if (menuNumber == exitProgram) {
                System.out.println("--> ALERT: Program will exit. See you again");
                System.exit(0);
                return false;
            } else {
                System.out.println("--> WARNING: Can only enter 0 to 2. Please try again");
                return false;
            }
        } else {
            System.out.println("--> WARNING: Can only enter integer. Please try again");
            return false;
        }
    }

    /**
     * Validate input choose number of item in menu
     *
     * @param totalItem:    total item in menu
     * @param chooseNumber: choose number in menu
     * @return 1 if valid choose item, 2 if back main menu, 0 if exit program and -1 if invalid choose item
     */
    public int validateChooseItem(int totalItem, String chooseNumber) {
        if (isPositiveInteger(chooseNumber)) {
            int itemNumber = Integer.parseInt(chooseNumber);
            int exitProgram = 0;
            if (itemNumber > 0 && itemNumber <= totalItem) {
                return 1;
            } else if (itemNumber == exitProgram) {
                System.out.println("--> ALERT: Program will exit. See you again");
                System.exit(0);
                return 0;
            } else {
                System.out.println("--> WARNING: Can only enter 0 to " + totalItem + ". Please try again");
                return -1;
            }
        } else {
            if (chooseNumber.equals("b")) {
                return 2;
            }
            System.out.println("--> WARNING: Can only enter integer. Please try again");
            return -1;
        }
    }

    /**
     * Get name of type menu
     * @param typeNumber: number of type menu
     * @return name of type menu
     */
    public String getTypeMenuName(int typeNumber) {
        return TypeMenu.getNameByNumber(typeNumber);
    }

    /**
     * Order menu item. It will get at bill list
     *
     * @param menu:       drink menu or food menu
     * @param itemNumber: item number
     * @param quantity:   quantity of item
     */
    public void orderMenuItem(String menu, int itemNumber, int quantity) {
        this.menuService.orderMenuItem(menu, itemNumber, quantity);
    }

    /**
     * Validate input choose function in menu management
     *
     * @param chooseNumber: choose number of function
     * @return true if valid choose number
     */
    public boolean isValidChooseFunction(String chooseNumber) {
        if (isPositiveInteger(chooseNumber)) {
            int funcNumber = Integer.parseInt(chooseNumber);
            if (funcNumber > 0 && funcNumber <= 4) {
                return true;
            } else if (funcNumber == 0) {
                System.out.println("--> ALERT: Program will exit. See you again");
                System.exit(0);
                return true;
            } else {
                System.out.println("--> WARNING: Can only enter 0 to 4. Please try again");
                return false;
            }
        } else {
            System.out.println("--> WARNING: Can only enter the integer. Please try again");
            return false;
        }
    }

    /**
     * Validate input type menu number
     *
     * @param typeMenuNumber: type menu number
     * @return true if valid type menu number
     */
    public boolean isValidTypeMenuNumber(String typeMenuNumber) {
        if (isPositiveInteger(typeMenuNumber)) {
            int number = Integer.parseInt(typeMenuNumber);
            if (number > 0 && number <= 5) {
                return true;
            } else if (number == 0) {
                System.out.println("--> ALERT: Program will exit. See you again");
                System.exit(0);
                return true;
            } else {
                System.out.println("--> WARNING: Can only enter 0 to 5. Please try again");
                return false;
            }
        } else {
            System.out.println("--> WARNING: Can only enter integer. Please try again");
            return false;
        }
    }

    /**
     * Valid exit management
     *
     * @param chooseNumber: number of exit or back
     */
    public int handleExitManagement(String chooseNumber) {
        final String exitProgram = "0";
        final String backManagement = "b";
        switch (chooseNumber) {
            case exitProgram -> {
                System.out.println("--> ALERT: Program will exit. See you again");
                System.exit(0);
                return 0;
            }
            case backManagement -> {
                return 1;
            }
            default -> {
                System.out.println("--> WARNING: Can only enter '0' or 'b'. Please try again");
                return -1;
            }
        }
    }

    /**
     * Handle exit menu
     *
     * @param chooseNumber: choose number
     * @return 0 if you want exit program, 1 if you want back main menu, -1 if invalid choose number
     */
    public int handleExitMenu(String chooseNumber) {
        final String exitProgram = "0";
        final String backMenu = "b";
        switch (chooseNumber) {
            case exitProgram -> {
                System.out.println("--> ALERT: Program will exit. See you again");
                this.menuService.exportBill();
                System.exit(0);
                return 0;
            }
            case backMenu -> {
                return 1;
            }
            default -> {
                System.out.println("--> WARNING: Can only enter '0' or 'b'. Please try again");
                return -1;
            }
        }
    }

    /**
     * Show all item of menu list by menu file
     *
     * @param fileName: name of menu data
     * @return list of menu file
     */
    public List<MenuModel> showMenuList(String fileName) {
        return this.menuService.getMenuList(fileName);
    }

    /**
     * Check for existence of item name for input add item
     *
     * @param name:           item name
     * @param typeMenuNumber: type menu number
     * @return true if item name already exist
     */
    public boolean hasAddItemName(String name, int typeMenuNumber) {
        TypeMenu type = TypeMenu.getTypeByNumber(typeMenuNumber);
        if (this.menuService.hasItemName(name, type)) {
            System.out.println("--> WARNING: Item name already exist. Please try again");
            return true;
        } else {
            return false;
        }
    }


    /**
     * Valid and send item information to add item at menu
     *
     * @param name:           item name
     * @param description:    item description
     * @param price:          item price
     * @param typeMenuNumber: item type
     * @return true if valid item information
     */
    public boolean addItem(String name, String description, String price, int typeMenuNumber) {
        int nameLength = name.trim().length();
        int descriptionLength = description.trim().length();
        int priceLength = price.trim().length();

        if (nameLength == 0 && descriptionLength == 0 && priceLength == 0) {
            System.out.println("--> WARNING: Can not empty information. Please try again");
            return false;
        } else if (nameLength == 0 || descriptionLength == 0 || priceLength == 0) {
            System.out.println("--> WARNING: Only enter full information. Please try again");
            return false;
        } else {
            if (isNumeric(price)) {
                TypeMenu type = TypeMenu.getTypeByNumber(typeMenuNumber);
                MenuModel menuModel = new MenuModel(name, description, Double.parseDouble(price), type);
                return this.menuService.addItem(menuModel);
            } else {
                System.out.println("--> WARNING: Can only price input is numeric. Please try again");
                return false;
            }
        }
    }

    /**
     * Get list of menu to show menu list
     *
     * @param typeMenuNumber: type menu number
     * @return list of menu
     */
    public List<MenuModel> showMenuList(int typeMenuNumber) {
        if (this.isValidTypeMenuNumber(String.valueOf(typeMenuNumber))) {
            TypeMenu type = TypeMenu.getTypeByNumber(typeMenuNumber);
            return this.menuService.getMenuList(type);
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Check item name has been existed
     *
     * @param name:           item name
     * @param typeMenuNumber: type menu number
     * @return true if item name has been existed
     */
    public boolean hasItemName(String name, int typeMenuNumber) {
        TypeMenu type = TypeMenu.getTypeByNumber(typeMenuNumber);
        if (this.menuService.hasItemName(name, type)) {
            return true;
        } else {
            System.out.println("--> WARNING: Item name dose not exist. Please try again");
            return false;
        }
    }

    /**
     * Check new name of name want update has been existed
     *
     * @param name:           name item
     * @param typeMenuNumber: type menu number
     * @return true if update item name already exist
     */
    public boolean hasUpdateItemName(String name, int typeMenuNumber) {
        TypeMenu typeMenu = TypeMenu.getTypeByNumber(typeMenuNumber);
        if (this.menuService.hasItemName(name, typeMenu)) {
            System.out.println("--> WARNING: Item name already exist. Please try again");
            return true;
        } else if (name.trim().equals("n")) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * Valid and send item information to update item
     *
     * @param nameWantUpdate: name want update
     * @param name:           new name
     * @param description:    new description
     * @param price:          new price
     * @param typeMenuNumber: type menu number
     * @return true if valid item information
     */
    public boolean isUpdateItem(String nameWantUpdate, String name, String description, String price, int typeMenuNumber) {
        int nameLength = name.trim().length();
        int descriptionLength = description.trim().length();
        int priceLength = price.trim().length();

        if (nameLength == 0 && descriptionLength == 0 && priceLength == 0) {
            System.out.println("--> WARNING: Can not empty information. Please try again");
            return false;
        } else if (nameLength == 0 || descriptionLength == 0 || priceLength == 0) {
            System.out.println("--> WARNING: Only enter full information. Please try again");
            return false;
        } else {
            if (isNumeric(price) || price.equals("n")) {
                TypeMenu type = TypeMenu.getTypeByNumber(typeMenuNumber);
                double validPrice = price.equals("n") ? 0 : Double.parseDouble(price);
                MenuModel menuModel = new MenuModel(name, description, validPrice, type);

                return this.menuService.isUpdateItem(nameWantUpdate, menuModel);
            } else {
                System.out.println("--> WARNING: Can only price input is numeric. Please try again");
                return false;
            }
        }
    }

    /**
     * Valid and send item information to remove item
     *
     * @param name:           item name
     * @param typeMenuNumber: type menu number
     * @return true if valid item information
     */
    public boolean removeItem(String name, int typeMenuNumber) {
        TypeMenu type = TypeMenu.getTypeByNumber(typeMenuNumber);
        if (this.menuService.hasItemName(name, type)) {
            this.menuService.removeItem(name, type);
            return true;
        } else {
            System.out.println("--> WARNING: Item name dose not exist. Please try again");
            return false;
        }
    }
}
