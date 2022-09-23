package business.logic.layer;

import java.util.Scanner;

public class DrinkMenuBusiness {
    public void showMenu() {
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

    public void chooseItem() {
        SoftDinkBusiness softDinkBusiness = new SoftDinkBusiness();
        MenuBusiness menuBusiness = new MenuBusiness();
        AlcoholBusiness alcoholBusiness = new AlcoholBusiness();

        System.out.print("Choose drink menu by number or 'back' or 'exit': ");
        Scanner scannerNumber = new Scanner(System.in);
        try {
            int choose = scannerNumber.nextInt();
            switch (choose) {
                case 1 -> {
                    softDinkBusiness.showMenu();
                    softDinkBusiness.showChooseItem();
                }
                case 2 -> {
                    alcoholBusiness.showMenu();
                    alcoholBusiness.showChooseItem();
                }
                default -> {
                    System.out.println("ERROR: Invalid number. Please re-enter for Drink Menu.");
                    this.chooseItem();
                }
            }
        } catch (Exception e) {
            String choose = scannerNumber.nextLine();
            switch (choose) {
                case "back" -> {
                    menuBusiness.showMenu();
                    menuBusiness.showChooseItem();
                }
                case "exit" -> System.exit(0);
                default -> {
                    System.out.println("ERROR: Invalid input data. Please re-enter for Drink Menu.");
                    this.chooseItem();
                }
            }
        }
    }

    public void showManagement() {
        System.out.format("+------------------ Drink Management --------------+%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| # | Function    | Description                    |%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| 1 | Soft Drink  | Soft Drink Management          |%n");
        System.out.format("| 2 | Alcohol     | Alcohol Management             |%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| Enter 'exit'    | Exit program                   |%n");
        System.out.format("+-----------------+--------------------------------+%n");
    }

    public void chooseFunction(){
        SoftDinkBusiness softDinkBusiness = new SoftDinkBusiness();
        AlcoholBusiness alcoholBusiness = new AlcoholBusiness();

        Scanner scannerFunction = new Scanner(System.in);

        System.out.print("Enter the function by number: ");
        try {
            int choose = scannerFunction.nextInt();
            switch (choose) {
                case 1 -> {
                    softDinkBusiness.showManagement();
                    softDinkBusiness.chooseFunction();
                }
                case 2 -> {
                    alcoholBusiness.showManagement();
                    alcoholBusiness.chooseFunction();
                }
                default -> {
                    System.out.println("Invalid number. Please try again");
                    this.chooseFunction();
                }
            }
        } catch (Exception e) {
            String choose = scannerFunction.nextLine();
            if ("exit".equalsIgnoreCase(choose)) {
                System.exit(0);
            } else {
                System.out.println("Invalid input data. Please try again");
                this.chooseFunction();
            }
        }
    }
}
