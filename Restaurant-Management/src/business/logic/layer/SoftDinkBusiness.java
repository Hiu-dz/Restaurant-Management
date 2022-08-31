package business.logic.layer;

import data.access.layer.SoftDrink;

import java.util.*;

public class SoftDinkBusiness {
    private final List<SoftDrink> softDinkBusinesses;
    public SoftDinkBusiness() {
        this.softDinkBusinesses = softDinkBusinessList();
    }

    public List<SoftDrink> getSoftDinkBusinesses() {
        return softDinkBusinesses;
    }

    public List<SoftDrink> softDinkBusinessList() {
        List<SoftDrink> softDrinkList = new ArrayList<>();
        softDrinkList.add(new SoftDrink("Coca Cola", "Drink from USA", 12000));
        softDrinkList.add(new SoftDrink("Pepsi", "Drink from USA", 10000));
        softDrinkList.add(new SoftDrink("Number One", "Drink from Vietnam", 15000));
        softDrinkList.add(new SoftDrink("Red Bull", "Drink from Thailand", 18000));

        return softDrinkList;
    }

    public void addItemToSoftDrink(String softDrinkName, String softDrinkDescription, double softDrinkPrice) {
        softDinkBusinesses.add(new SoftDrink(softDrinkName, softDrinkDescription, softDrinkPrice));
    }

    public void displaySoftDrinkMenu() {
        System.out.format("+-------------------- Choose Soft Drink Menu ---------------------+%n");
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        System.out.format("| # | Name       | Description                        | Price     |%n");
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        int i = 1;
        for (SoftDrink m : getSoftDinkBusinesses()) {
            System.out.format("| %s |%-12s| %-35s| %-10s| %n", i++, m.getName(), m.getDescription(), m.getPrice());
        }
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        System.out.format("| Enter 'back'   | Back to drink menu                             |%n");
        System.out.format("| Enter 'exit'   | Exit program                                   |%n");
        System.out.format("+----------------+------------------------------------------------+%n");
    }

    public void chooseSoftDrinkMenu() {
        DrinkMenuBusiness drinkMenuBusiness = new DrinkMenuBusiness();

        List<SoftDrink> softDrinks = this.softDinkBusinessList();

        System.out.println("Choose soft drink menu by number or 'back' or 'exit': ");
        Scanner scanner = new Scanner(System.in);

        try {
            int chooseMenu = scanner.nextInt();

            for (int i = 0; i < softDrinks.size(); i++) {
                if (chooseMenu == i + 1) {
                    System.out.printf(String.valueOf(softDrinks.get(i)));
                }
            }
            if (chooseMenu > (softDrinks.size()) || chooseMenu == 0) {
                System.out.println("ERROR: Invalid number. Please re-enter for Soft Drink Menu.");
                this.chooseSoftDrinkMenu();
            }
        } catch (Exception e) {
            String chooseMenu = scanner.nextLine();
            switch (chooseMenu) {
                case "back":
                    drinkMenuBusiness.displayDrinkMenu();
                    drinkMenuBusiness.chooseDrinkMenu();
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("ERROR: Invalid input data. Please re-enter for Soft Drink Menu.");
                    this.chooseSoftDrinkMenu();
                    break;
            }
        }
    }
}
