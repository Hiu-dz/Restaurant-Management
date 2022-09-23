package business.logic.layer;

import data.access.layer.DrinkData;
import data.access.layer.FoodData;

import java.util.Scanner;

public class MenuBusiness {
    private String[] getInformation() {
        Scanner scannerName = new Scanner(System.in);
        Scanner scannerDescription = new Scanner(System.in);
        Scanner scannerPrice = new Scanner(System.in);
        Scanner scannerType = new Scanner(System.in);

        System.out.print("Enter the name: ");
        String name = scannerName.nextLine();
        System.out.print("Enter the description: ");
        String description = scannerDescription.nextLine();
        System.out.print("Enter the price: ");
        String price = scannerPrice.nextLine();
        System.out.print("Enter the type: ");
        String type = scannerType.nextLine();

        return new String[]{
                name, description, price, type
        };
    }

    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        return str.matches("\\d+");
    }

    private void chooseMenu(int chooseNumber) {
        final int drinkMenu = 1;
        final int foodMenu = 2;
        final int exitProgram = 3;

        switch (chooseNumber) {
            case drinkMenu -> {
                System.out.println("Drink menu is ok.");
            }
            case foodMenu -> {
                System.out.println("Food menu is ok.");
            }
            case exitProgram -> {
                System.out.println("Exit program is ok.");
            }
            default -> System.out.println("--> ERROR: Something went wrong.");
        }
    }

    public void showMenu() {
        Scanner scannerChoose = new Scanner(System.in);

        System.out.println("---------- Welcome to Hiu 2nd restaurant ----------");
        System.out.println("----- Menu -----");
        System.out.println("1. Drink menu");
        System.out.println("2. Food menu");
        System.out.println("3. Exit program");

        try {
            System.out.print("Enter choose number: ");
            int chooseNumber = scannerChoose.nextInt();
            if (chooseNumber >= 1 && chooseNumber <= 3) {
                this.chooseMenu(chooseNumber);
            } else {
                System.out.println("->> ALERT: Invalid choose number.");
                System.out.println("--> Please try again.");
                this.showMenu();
            }
        } catch (Exception e) {
            System.out.println("--> ALERT: Can only enter the choose number as integer.");
            System.out.println("--> Please try again.");
            this.showMenu();
        }
    }

    private void addItem(String name, String description, double price, int type) {
        final int softDrink = 1;
        final int alcohol = 2;
        final int breakfast = 3;
        final int lunch = 4;
        final int dinner = 5;
        switch (type) {
            case softDrink -> {
                String trueType = "Soft drink";
                DrinkData newSoftDrink = new DrinkData(name, description, price, trueType);
                DrinkData.getDrinkDataList().add(newSoftDrink);
            }
            case alcohol -> {
                String trueType = "Alcohol";
                DrinkData newAlcohol = new DrinkData(name, description, price, trueType);
                DrinkData.getDrinkDataList().add(newAlcohol);
            }
            case breakfast -> {
                String trueType = "Breakfast";
                FoodData newBreakfast = new FoodData(name, description, price, trueType);
                FoodData.getFoodDataList().add(newBreakfast);
            }
            case lunch -> {
                String trueType = "Lunch";
                FoodData newLunch = new FoodData(name, description, price, trueType);
                FoodData.getFoodDataList().add(newLunch);
            }
            case dinner -> {
                String trueType = "Dinner";
                FoodData newDinner = new FoodData(name, description, price, trueType);
                FoodData.getFoodDataList().add(newDinner);
            }
            default -> System.out.println("--> ERROR: Something went wrong.");
        }
    }

    public void addItem() {
        String[] information = this.getInformation();
        String name = information[0];
        String description = information[1];
        String price = information[2];
        String type = information[3];

        if (isNumeric(type)) {
            double truePrice = Double.parseDouble(price);
            int trueType = Integer.parseInt(type);
            if (trueType >= 1 && trueType <= 5) {
                if (isNumeric(price)) {
                    this.addItem(name, description, truePrice, trueType);
                } else {
                    System.out.println("--> ALERT: Can only enter the price as numeric.");
                    System.out.println("--> Please try again.");
                    this.addItem();
                }
            } else {
                System.out.println("--> ALERT: Can only enter the type is available in the type menu.");
                System.out.println("--> Please try again.");
                this.addItem();
            }
        } else {
            System.out.println("--> ALERT: Can only enter the type as numeric.");
            System.out.println("--> Please try again.");
            this.addItem();
        }
    }

}
