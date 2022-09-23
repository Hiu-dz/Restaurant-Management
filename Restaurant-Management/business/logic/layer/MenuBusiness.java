package business.logic.layer;

import java.util.Scanner;

/**
 * This is a class to build functions for Menu.
 */
public class MenuBusiness {
    public void showMenu() {
        System.out.format("+--------------------- Choose Menu -------------------+%n");
        System.out.format("+---+------------+------------------------------------+%n");
        System.out.format("| # | Name       | Description                        |%n");
        System.out.format("+---+------------+------------------------------------+%n");
        System.out.format("| 1 | Drink Menu | Menu for drinks                    |%n");
        System.out.format("| 2 | Food Menu  | Menu for foods                     |%n");
        System.out.format("+---+------------+------------------------------------+%n");
        System.out.format("| Enter 'exit'   | Exit program                       |%n");
        System.out.format("+---+------------+------------------------------------+%n");
    }

    public void showChooseItem() {
        DrinkMenuBusiness drinkMenuBusiness = new DrinkMenuBusiness();
        FoodMenuBusiness foodMenuBusiness = new FoodMenuBusiness();

        System.out.print("Choose menu by number: ");
        Scanner scannerNumber = new Scanner(System.in);
        try {
            int chooseMenu = scannerNumber.nextInt();
            final int DRINK_MENU = 1;
            final int FOOD_MENU = 2;

            switch (chooseMenu) {
                case DRINK_MENU -> {
                    drinkMenuBusiness.showMenu();
                    drinkMenuBusiness.chooseItem();
                }
                case FOOD_MENU -> {
                    foodMenuBusiness.showMenu();
                    foodMenuBusiness.showChooseMenu();
                }
                default -> {
                    System.out.println("ERROR: Invalid number. Please re-enter for Menu.");
                    this.showChooseItem();
                }
            }
        } catch (Exception e) {
            String chooseMenu = scannerNumber.nextLine();
            if ("exit".equalsIgnoreCase(chooseMenu)) {
                System.exit(0);
            } else {
                System.out.println("ERROR: Invalid input data. Please re-enter for Menu.");
                this.showChooseItem();
            }
        }
    }

    public void showManagement() {
        System.out.format("+---------------- Choose Menu Management -------------+%n");
        System.out.format("+---+------------------+------------------------------+%n");
        System.out.format("| # | Name             | Description                  |%n");
        System.out.format("+---+------------------+------------------------------+%n");
        System.out.format("| 1 | Drink Management | Drinks management            |%n");
        System.out.format("| 2 | Food Management  | Foods management             |%n");
        System.out.format("+---+------------------+------------------------------+%n");
        System.out.format("| Enter 'exit'         | Exit program                 |%n");
        System.out.format("+---+------------------+------------------------------+%n");
    }

    public void chooseFunction() {
        DrinkMenuBusiness drinkMenuBusiness = new DrinkMenuBusiness();
        FoodMenuBusiness foodMenuBusiness = new FoodMenuBusiness();

        Scanner scannerChoose = new Scanner(System.in);
        System.out.print("Enter the number function or 'exit': ");
        try {
            int choose = scannerChoose.nextInt();
            final int DRINK_MANAGEMENT = 1;
            final int FOOD_MANAGEMENT = 2;

            switch (choose) {
                case DRINK_MANAGEMENT -> {
                    drinkMenuBusiness.showManagement();
                    drinkMenuBusiness.chooseFunction();
                }
                case FOOD_MANAGEMENT -> {
                    foodMenuBusiness.showManagement();
                    foodMenuBusiness.chooseFunction();
                }
                default -> {
                    System.out.println("ERROR: Invalid number. Please try again");
                    this.chooseFunction();
                }
            }
        } catch (Exception e) {
            String choose = scannerChoose.nextLine();
            if (choose.equalsIgnoreCase("exit")) {
                System.exit(0);
            } else {
                System.out.println("ERROR: Invalid input data. Please try again.");
                this.chooseFunction();
            }
        }

    }
}
