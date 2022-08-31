package business.logic.layer;

import java.util.Scanner;

public class DrinkMenuBusiness {
    public void displayDrinkMenu() {
        System.out.format("+------------------ Choose Drink Menu ----------------+%n");
        System.out.format("+---+------------+------------------------------------+%n");
        System.out.format("| # | Name       | Description                        |%n");
        System.out.format("+---+------------+------------------------------------+%n");
        System.out.format("| 1 | Soft Drink | Menu for soft drink                |%n");
        System.out.format("| 2 | Alcohol    | Menu for alcohol                   |%n");
        System.out.format("+---+------------+------------------------------------+%n");
        System.out.format("| Enter 'back'   | Back to main menu                  |%n");
        System.out.format("| Enter 'exit'   | Exit program                       |%n");
        System.out.format("+---+------------+------------------------------------+%n");
    }

    public void chooseDrinkMenu() {
        SoftDinkBusiness softDinkBusiness = new SoftDinkBusiness();
        MenuBusiness menuBusiness = new MenuBusiness();

        System.out.println("Choose drink menu by number or 'back' or 'exit': ");
        Scanner scannerNumber = new Scanner(System.in);
        try {
            int chooseMenu = scannerNumber.nextInt();
            switch (chooseMenu) {
                case 1:
                    softDinkBusiness.displaySoftDrinkMenu();
                    softDinkBusiness.chooseSoftDrinkMenu();
                    break;
                case 2:
                    System.out.println("2 is ok");
                    break;
                default:
                    System.out.println("ERROR: Invalid number. Please re-enter for Drink Menu.");
                    this.chooseDrinkMenu();
                    break;
            }
        } catch (Exception e) {
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
                    System.out.println("ERROR: Invalid input data. Please re-enter for Drink Menu.");
                    this.chooseDrinkMenu();
                    break;
            }
        }
    }
}
