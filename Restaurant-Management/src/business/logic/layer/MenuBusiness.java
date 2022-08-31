package business.logic.layer;

import data.access.layer.Menu;

import java.util.Scanner;

/**
 * This is a class to build functions for Menu.
 */
public class MenuBusiness {
    public void displayMenu() {
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

    public void chooseMenu() {
        DrinkMenuBusiness drinkMenuBusiness = new DrinkMenuBusiness();
        FoodMenuBusiness foodMenuBusiness = new FoodMenuBusiness();

        System.out.println("Choose menu by number: ");
        Scanner scannerNumber = new Scanner(System.in);
        try {
            int chooseMenu = scannerNumber.nextInt();
            switch (chooseMenu) {
                case 1:
                    drinkMenuBusiness.displayDrinkMenu();
                    drinkMenuBusiness.chooseDrinkMenu();
                    break;
                case 2:
                    foodMenuBusiness.displayFoodMenu();
                    foodMenuBusiness.chooseFoodMenu();
                    break;
                default:
                    System.out.println("ERROR: Invalid number. Please re-enter for Menu.");
                    this.chooseMenu();
                    break;
            }
        } catch (Exception e) {
            String chooseMenu = scannerNumber.nextLine();
            if ("exit".equals(chooseMenu)) {
                System.exit(0);
            } else {
                System.out.println("ERROR: Invalid input data. Please re-enter for Menu.");
                this.chooseMenu();
            }
        }
    }
}
