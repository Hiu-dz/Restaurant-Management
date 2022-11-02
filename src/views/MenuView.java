package views;

import controllers.MenuController;
import models.MenuModel;
import models.TypeMenu;

import java.util.*;

public class MenuView {
    private final MenuController menuController;

    public MenuView() {
        this.menuController = new MenuController();
    }

    /**
     * Exit management when using menu management
     */
    private void exitManagement() {
        Scanner scExitHere = new Scanner(System.in);

        System.out.println("0. Exit program");
        System.out.println("b. Back to menu management");
        System.out.println("------");
        System.out.print("Enter your choose: ");
        String chooseExit = scExitHere.nextLine();

        boolean isBackManagement = this.menuController.handleExitManagement(chooseExit) == 1;
        boolean isWarning = this.menuController.handleExitManagement(chooseExit) == -1;
        if (isBackManagement) {
            this.showManagement();
        } else if (isWarning) {
            this.exitManagement();
        }
    }

    /**
     * Exit menu or back main menu
     */
    private void exitMenu() {
        Scanner scExitHere = new Scanner(System.in);

        System.out.println("0. Exit program");
        System.out.println("b. Back to main menu");
        System.out.println("------");
        System.out.print("Enter your choose: ");
        String chooseExit = scExitHere.nextLine();

        int chooseOptions = this.menuController.handleExitMenu(chooseExit);
        int backMenu = 1;
        int warning = -1;
        if (chooseOptions == backMenu) {
            this.showMenu();
        } else if (chooseOptions == warning) {
            this.exitMenu();
        }
    }

    /**
     * Show main menu
     */
    public void showMenu() {
        System.out.println("---- ORDER MENU ITEM ----");
        System.out.println("1. Drink menu");
        System.out.println("2. Food menu");
        System.out.println("------");
        System.out.println("0. Exit program");
        System.out.println("b. Back system");
        System.out.println("------");

        this.chooseMenu();
    }

    /**
     * Choose menu number of main menu
     */
    private void chooseMenu() {
        MainView mainView = new MainView();
        Scanner scChooseNumber = new Scanner(System.in);

        System.out.print("Enter the choose number: ");
        String choose = scChooseNumber.nextLine();
        int number = this.menuController.validateChooseMenu(choose);
        int drinkMenu = 1;
        int foodMenu = 2;
        int backSystem = 3;
        int warning = -1;
        if (number == drinkMenu) {
            this.showDrinkMenu();
        } else if (number == foodMenu) {
            this.showFoodMenu();
        } else if (number == backSystem) {
            mainView.showSystem();
        } else if (number == warning) {
            this.chooseMenu();
        }
    }

    /**
     * Show drink menu
     */
    private void showDrinkMenu() {
        List<MenuModel> softDrinks = this.menuController.showMenuList(TypeMenu.SOFT_DRINK.getFile());
        List<MenuModel> alcohols = this.menuController.showMenuList(TypeMenu.ALCOHOL.getFile());
        int totalItem = softDrinks.size() + alcohols.size();

        System.out.println("---- DRINK MENU ----");
        System.out.println("**Soft drink menu**");
        for (int i = 0; i < softDrinks.size(); i++) {
            int itemNumber = i + 1;
            String name = softDrinks.get(i).getName();
            double price = softDrinks.get(i).getPrice();
            System.out.println(itemNumber + ". " + name + " - " + price + " vnd");
        }
        System.out.println("**Alcohol menu**");
        for (int i = 0; i < alcohols.size(); i++) {
            int itemNumber = (softDrinks.size() + i) + 1;
            String name = alcohols.get(i).getName();
            double price = alcohols.get(i).getPrice();
            System.out.println(itemNumber + ". " + name + " - " + price + " vnd");
        }
        System.out.println("------");
        System.out.println("0. Exit program");
        System.out.println("b. Back main menu");
        System.out.println("------");
        this.chooseItem("DrinkMenu", totalItem);
    }

    /**
     * Show food menu
     */
    private void showFoodMenu() {
        List<MenuModel> breakfasts = this.menuController.showMenuList(TypeMenu.BREAKFAST.getFile());
        List<MenuModel> lunches = this.menuController.showMenuList(TypeMenu.LUNCH.getFile());
        List<MenuModel> dinners = this.menuController.showMenuList(TypeMenu.DINNER.getFile());
        int totalItem = breakfasts.size() + lunches.size() + dinners.size();

        System.out.println("---- FOOD MENU ----");
        System.out.println("**Breakfast menu**");
        for (int i = 0; i < breakfasts.size(); i++) {
            int itemNumber = i + 1;
            String name = breakfasts.get(i).getName();
            double price = breakfasts.get(i).getPrice();
            System.out.println(itemNumber + ". " + name + " - " + price + " vnd");
        }
        System.out.println("**Lunch menu**");
        for (int i = 0; i < lunches.size(); i++) {
            int itemNumber = (breakfasts.size() + i) + 1;
            String name = lunches.get(i).getName();
            double price = lunches.get(i).getPrice();
            System.out.println(itemNumber + ". " + name + " - " + price + " vnd");
        }
        System.out.println("**Dinner menu**");
        for (int i = 0; i < dinners.size(); i++) {
            int itemNumber = (breakfasts.size() + lunches.size() + i) + 1;
            String name = dinners.get(i).getName();
            double price = dinners.get(i).getPrice();
            System.out.println(itemNumber + ". " + name + " - " + price + " vnd");
        }
        System.out.println("------");
        System.out.println("0. Exit program");
        System.out.println("b. Back main menu");
        System.out.println("------");
        this.chooseItem("FoodMenu", totalItem);
    }

    /**
     * Choose item number of menu
     *
     * @param totalItem: total item of menu
     */
    private void chooseItem(String typeMenu, int totalItem) {
        Scanner scChooseItem = new Scanner(System.in);
        Scanner scEnterQuantity = new Scanner(System.in);
        try {
            System.out.print("Enter the item number: ");
            String itemNumber = scChooseItem.nextLine();
            int chooseOption = this.menuController.validateChooseItem(totalItem, itemNumber);
            int validate = 1;
            int backMainMenu = 2;
            int warning = -1;
            if (chooseOption == validate) {
                System.out.print("Enter the quantity: ");
                int itemQuantity = scEnterQuantity.nextInt();
                this.menuController.orderMenuItem(typeMenu, Integer.parseInt(itemNumber), itemQuantity);
                System.out.println("------");
                this.chooseOptionWhenOrdered();
            } else if (chooseOption == warning) {
                this.chooseItem(typeMenu, totalItem);
            } else if (chooseOption == backMainMenu) {
                this.showMenu();
            }
        } catch (InputMismatchException e) {
            this.chooseItem(typeMenu, totalItem);
        }
    }

    /**
     * Choose options when ordered
     */
    private void chooseOptionWhenOrdered() {
        Scanner scChoose = new Scanner(System.in);

        System.out.println("--> QUESTION: Do you want to continue ordering ?");
        System.out.println("1. Yes (Back order menu item)");
        System.out.println("2. No (Back system)");
        System.out.println("0. Exit program");
        System.out.println("------");
        System.out.print("Enter your choose number: ");
        String choose = scChoose.nextLine();

        int number = this.menuController.validateChooseOptionWhenOrdered(choose);
        int continueOrder = 1;
        int finishOrder = 2;
        int warning = -1;
        if (number == continueOrder) {
            this.showMenu();
        } else if (number == finishOrder) {
            this.chooseOptionWhenPrintBill();
        } else if (number == warning) {
            this.chooseOptionWhenOrdered();
        }
    }

    /**
     * Choose options when print bill
     */
    public void chooseOptionWhenPrintBill() {
        MainView mainView = new MainView();
        Scanner scChoose = new Scanner(System.in);

        System.out.println("1. Back system");
        System.out.println("0. Exit program");
        System.out.println("------");
        System.out.print("Enter your choose number: ");
        String choose = scChoose.nextLine();
        int number = this.menuController.validateChooseWhenPrintBill(choose);
        int backSystem = 1;
        int warning = -1;
        if (number == backSystem) {
            mainView.showSystem();
        } else if (number == warning) {
            this.chooseOptionWhenPrintBill();
        }
    }

    /**
     * Show menu management
     */
    public void showManagement() {
        System.out.println("---- MENU MANAGEMENT ----");
        System.out.println("1. Show menu list");
        System.out.println("2. Add item");
        System.out.println("3. Update item");
        System.out.println("4. Remove item");
        System.out.println("------");
        System.out.println("0. Exit program");
        System.out.println("b. Back system management");
        System.out.println("------");

        this.chooseFunction();
    }

    /**
     * Choose function in menu management
     */
    private void chooseFunction() {
        MainView mainView = new MainView();
        Scanner scChoose = new Scanner(System.in);

        System.out.print("Enter the choose number: ");
        String choose = scChoose.nextLine();
        int number = this.menuController.validateChooseFunction(choose);
        int functions = 1;
        int backSystemManagement = 2;
        int warning = -1;
        if (number == functions) {
            this.executeFunction(Integer.parseInt(choose));
        } else if (number == backSystemManagement) {
            mainView.showSystemManagement();
        } else if (number == warning) {
            this.chooseFunction();
        }
    }

    /**
     * Show types menu when chose a function in menu management
     */
    private void showTypeMenu() {
        System.out.println("---- TYPE MENU ----");
        System.out.println("1. Soft drink menu");
        System.out.println("2. Alcohol menu");
        System.out.println("3. Breakfast menu");
        System.out.println("4. Lunch menu");
        System.out.println("5. Dinner menu");
        System.out.println("------");
    }

    /**
     * Get type menu number
     *
     * @return type menu number
     */
    private int getTypeMenuNumber() {
        Scanner scType = new Scanner(System.in);

        System.out.print("Enter item type: ");
        String type = scType.nextLine();
        if (this.menuController.isValidTypeMenuNumber(type)) {
            return Integer.parseInt(type);
        } else {
            return -1;
        }
    }

    /**
     * Perform function in menu management
     *
     * @param funcNumber: function number
     */
    private void executeFunction(int funcNumber) {
        this.showTypeMenu();
        int type = this.getTypeMenuNumber();
        if (type == -1) {
            type = this.getTypeMenuNumber();
        }
        final int showMenuList = 1;
        final int addItem = 2;
        final int updateItem = 3;
        final int removeItem = 4;
        final int exitProgram = 0;
        switch (funcNumber) {
            case showMenuList -> {
                this.showMenuList(type);
                this.exitManagement();
            }
            case addItem -> {
                this.addItem(type);
                this.exitManagement();
            }
            case updateItem -> {
                this.updateItem(type);
                this.exitManagement();
            }
            case removeItem -> {
                this.removeItem(type);
                this.exitManagement();
            }
            case exitProgram -> {
                System.out.println("exit is ok");
            }
            default -> System.out.println("--> ERROR: Something went wrong");
        }
    }

    /**
     * Perform add item at menu
     *
     * @param typeMenuNumber: type menu number
     */
    private void addItem(int typeMenuNumber) {
        Scanner scName = new Scanner(System.in);
        Scanner scDescription = new Scanner(System.in);
        Scanner scPrice = new Scanner(System.in);

        String typeMenuName = this.menuController.getTypeMenuName(typeMenuNumber);
        System.out.println("---- " + typeMenuName + " - ADD ITEM ----");
        System.out.print("Enter item name: ");
        String name = scName.nextLine();
        if (!this.menuController.hasAddItemName(name, typeMenuNumber)) {
            System.out.print("Enter item description: ");
            String description = scDescription.nextLine();
            System.out.print("Enter item price: ");
            String price = scPrice.nextLine();

            if (!this.menuController.addItem(name, description, price, typeMenuNumber)) {
                this.addItem(typeMenuNumber);
            }
            System.out.println("------");
        } else {
            this.addItem(typeMenuNumber);
        }
    }

    /**
     * Perform show all item of menu
     *
     * @param typeMenuNumber: type menu number
     */
    private void showMenuList(int typeMenuNumber) {
        List<MenuModel> menuList = this.menuController.showMenuList(typeMenuNumber);

        String typeMenuName = this.menuController.getTypeMenuName(typeMenuNumber);
        System.out.println("------ " + typeMenuName + " - MENU LIST ------");
        if (!menuList.isEmpty()) {
            for (int i = 0; i < menuList.size(); i++) {
                int itemNumber = i + 1;
                String name = menuList.get(i).getName();
                String description = menuList.get(i).getDescription();
                double price = menuList.get(i).getPrice();
                System.out.println(itemNumber + ". " + name + " - " + description + " - " + price + " vnd");
            }
        } else {
            System.out.println("This list has no items. Please come back later");
        }
        System.out.println("------");
    }

    /**
     * Perform update item at menu
     *
     * @param typeMenuNumber: type menu number
     */
    private void updateItem(int typeMenuNumber) {
        Scanner scName = new Scanner(System.in);
        Scanner scNewName = new Scanner(System.in);
        Scanner scNewDescription = new Scanner(System.in);
        Scanner scNewPrice = new Scanner(System.in);

        String typeMenuName = this.menuController.getTypeMenuName(typeMenuNumber);
        System.out.println("------ " + typeMenuName + " - UPDATE ITEM ------");
        System.out.print("Enter the item name want update: ");
        String nameWantUpdate = scName.nextLine();
        if (this.menuController.hasItemName(nameWantUpdate, typeMenuNumber)) {
            System.out.print("Enter the new name or 'n' to next: ");
            String newName = scNewName.nextLine();
            while (this.menuController.hasUpdateItemName(newName, typeMenuNumber)) {
                System.out.print("Enter the new name or 'n' to next: ");
                newName = scNewName.nextLine();
            }
            System.out.print("Enter the new description or 'n' to next: ");
            String newDescription = scNewDescription.nextLine();
            System.out.print("Enter the new price or 'n' to next: ");
            String newPrice = scNewPrice.nextLine();

            if (!this.menuController.isUpdateItem(nameWantUpdate, newName, newDescription, newPrice, typeMenuNumber)) {
                this.updateItem(typeMenuNumber);
            }
        } else {
            this.updateItem(typeMenuNumber);
        }
    }

    /**
     * Perform remove item at menu
     *
     * @param typeMenuNumber: type menu number
     */
    public void removeItem(int typeMenuNumber) {
        Scanner scRemove = new Scanner(System.in);

        String typeMenuName = this.menuController.getTypeMenuName(typeMenuNumber);
        System.out.println("------ " + typeMenuName + " - REMOVE ITEM ------");
        System.out.print("Enter the item name want remove: ");
        String name = scRemove.nextLine();
        if (!this.menuController.removeItem(name, typeMenuNumber)) {
            this.removeItem(typeMenuNumber);
        }
    }
}
