package business.logic.layer;

import java.util.Scanner;

public class FoodMenuBusiness {
    public void showMenu() {
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

    public void showChooseMenu() {
        MenuBusiness menuBusiness = new MenuBusiness();
        BreakfastMenuBusiness breakfastMenuBusiness = new BreakfastMenuBusiness();
        LunchMenuBusiness lunchMenuBusiness = new LunchMenuBusiness();
        DinnerMenuBusiness dinnerMenuBusiness = new DinnerMenuBusiness();

        System.out.print("Choose food menu by number: ");
        Scanner scannerNumber = new Scanner(System.in);
        try {
            int chooseMenu = scannerNumber.nextInt();
            final int BREAKFAST_MENU = 1;
            final int LUNCH_MENU = 2;
            final int DINNER_MENU = 3;

            switch (chooseMenu) {
                case BREAKFAST_MENU -> {
                    breakfastMenuBusiness.showMenu();
                    breakfastMenuBusiness.showChooseItem();
                }
                case LUNCH_MENU -> {
                    lunchMenuBusiness.showMenu();
                    lunchMenuBusiness.showChooseItem();
                }
                case DINNER_MENU -> {
                    dinnerMenuBusiness.showMenu();
                    dinnerMenuBusiness.showChooseItem();
                }
                default -> {
                    System.out.println("ERROR: Invalid number. Please re-enter for Food Menu.");
                    this.showChooseMenu();
                }
            }
        } catch (Exception e) {
            String chooseMenu = scannerNumber.nextLine();
            if (chooseMenu.equalsIgnoreCase("back")) {
                menuBusiness.showMenu();
                menuBusiness.showChooseItem();
            } else if (chooseMenu.equalsIgnoreCase("exit")) {
                System.exit(0);
            } else {
                System.out.println("ERROR: Invalid input data. Please re-enter for Food Menu.");
                this.showChooseMenu();
            }
        }
    }

    public void showManagement() {
        System.out.format("+------------------ Food Management --------------+%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| # | Function    | Description                    |%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| 1 | Breakfast   | Breakfast Management           |%n");
        System.out.format("| 2 | Lunch       | Lunch Management               |%n");
        System.out.format("| 3 | Dinner      | Dinner Management              |%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| Enter 'exit'    | Exit program                   |%n");
        System.out.format("+-----------------+--------------------------------+%n");
    }

    public void chooseFunction() {
        BreakfastMenuBusiness breakfastMenuBusiness = new BreakfastMenuBusiness();
        LunchMenuBusiness lunchMenuBusiness = new LunchMenuBusiness();
        DinnerMenuBusiness dinnerMenuBusiness = new DinnerMenuBusiness();

        Scanner scannerFunction = new Scanner(System.in);

        System.out.print("Enter the function by number: ");
        try {
            int choose = scannerFunction.nextInt();
            final int BREAKFAST_MANAGEMENT = 1;
            final int LUNCH_MANAGEMENT = 2;
            final int DINNER_MANAGEMENT = 3;

            switch (choose) {
                case BREAKFAST_MANAGEMENT -> {
                    breakfastMenuBusiness.showManagement();
                    breakfastMenuBusiness.chooseFunction();
                }
                case LUNCH_MANAGEMENT -> {
                    lunchMenuBusiness.showManagement();
                    lunchMenuBusiness.chooseFunction();
                }
                case DINNER_MANAGEMENT -> {
                    dinnerMenuBusiness.showManagement();
                    dinnerMenuBusiness.chooseFunction();
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
