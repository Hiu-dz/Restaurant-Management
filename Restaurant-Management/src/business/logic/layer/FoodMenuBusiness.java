package business.logic.layer;

import java.util.Scanner;

public class FoodMenuBusiness {
    public void displayFoodMenu() {
        System.out.format("+-------------------- Choose Food Menu -------------------+%n");
        System.out.format("+---+----------------+------------------------------------+%n");
        System.out.format("| # | Name           | Description                        |%n");
        System.out.format("+---+----------------+------------------------------------+%n");
        System.out.format("| 1 | Breakfast Menu | Menu for breakfast                 |%n");
        System.out.format("| 2 | Lunch Menu     | Menu for lunch                     |%n");
        System.out.format("| 3 | Dinner Menu    | Menu for dinner                    |%n");
        System.out.format("+---+----------------+------------------------------------+%n");
        System.out.format("| Enter 'back'       | Back to main menu                  |%n");
        System.out.format("| Enter 'exit'       | Exit program                       |%n");
        System.out.format("+--------------------+------------------------------------+%n");
    }

    public void chooseFoodMenu() {
        MenuBusiness menuBusiness = new MenuBusiness();

        System.out.println("Choose food menu by number: ");
        Scanner scannerNumber = new Scanner(System.in);
        try {
            int chooseMenu = scannerNumber.nextInt();
            switch (chooseMenu) {
                case 1:
                    System.out.println("1 is ok");
                    break;
                case 2:
                    System.out.println("2 is ok");
                    break;
                case 3:
                    System.out.println("3 is ok");
                    break;
                case 4:
                    menuBusiness.displayMenu();
                    menuBusiness.chooseMenu();
                    break;
                default:
                    System.out.println("ERROR: Invalid number. Please re-enter for Food Menu.");
                    this.chooseFoodMenu();
                    break;
            }
        } catch (Exception e) {
            try {
                String chooseMenu = scannerNumber.nextLine();
                switch (chooseMenu) {
                    case "back":
                        menuBusiness.displayMenu();
                        menuBusiness.chooseMenu();
                        break;
                    case "exit":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("ERROR: Invalid text. Please re-enter for Drink Menu.");
                        this.chooseFoodMenu();
                        break;
                }
            } catch (Exception e1) {
                System.out.println("ERROR: Invalid input data. Please re-enter for Food Menu.");
                this.chooseFoodMenu();
            }
        }
    }
}
