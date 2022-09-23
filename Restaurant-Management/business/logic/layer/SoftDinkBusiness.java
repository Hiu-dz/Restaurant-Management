package business.logic.layer;

import data.access.layer.SoftDrink;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SoftDinkBusiness {
    private final Path path = Paths.get("data\\menu\\SoftDrink.txt");

    public Path getPath() {
        return path;
    }

    private List<SoftDrink> softDrinkList() {
        List<String> stringList = new ArrayList<>();
        List<SoftDrink> softDrinkList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(getPath().toUri()))) {
            int i = 0;
            while (scanner.hasNext()) {
                stringList.add(scanner.nextLine());
                String[] words = stringList.get(i++).split(", ");
                softDrinkList.add(new SoftDrink(words[0], words[1], Double.parseDouble(words[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return softDrinkList;
    }

    private void writeFile(List<SoftDrink> listName) {
        try (BufferedWriter bw = Files.newBufferedWriter(getPath())) {
            for (SoftDrink s : listName) {
                bw.write(s.getName() + ", " + s.getDescription() + ", " + s.getPrice() + "\n");
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public boolean hasNameExist(String itemName) {
        return this.softDrinkList().stream().anyMatch(n -> itemName.equals(n.getName()));
    }

    protected boolean hasName(String itemName) {
        return this.softDrinkList().stream().anyMatch(n -> itemName.equalsIgnoreCase(n.getName()));
    }

    protected boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        return str.matches("\\d+");
    }

    protected void chooseItem(int itemNumber) {
        MenuBusiness menuBusiness = new MenuBusiness();
        BillManagementBusiness billManagementBusiness = new BillManagementBusiness();

        for (int i = 0; i < this.softDrinkList().size(); i++) {
            if (itemNumber == i + 1) {
                Scanner scannerQuantity = new Scanner(System.in);
                Scanner scannerChooseContinue = new Scanner(System.in);

                System.out.print("Enter " + this.softDrinkList().get(i).getName() + " quantity: ");
                String strQuantity = scannerQuantity.nextLine();
                while (!isNumeric(strQuantity)) {
                    System.out.println("ERROR: Invalid input data. Please try again");
                    System.out.print("Enter " + this.softDrinkList().get(i).getName() + " quantity: ");
                    strQuantity = scannerQuantity.nextLine();
                }
                String menuItem = this.softDrinkList().get(i).getName();
                int quantity = Integer.parseInt(strQuantity);
                double priceItems = this.softDrinkList().get(i).getPrice();
                billManagementBusiness.addItem(menuItem, quantity, priceItems);

                System.out.print("Enter 'y' to choose continue or 'n' to exit: ");
                String chooseContinue = scannerChooseContinue.nextLine();
                if (chooseContinue.equalsIgnoreCase("y")) {
                    menuBusiness.showMenu();
                    menuBusiness.showChooseItem();
                } else if (chooseContinue.equalsIgnoreCase("n")) {
                    billManagementBusiness.exportBill();
                    System.out.println("----- Thank you and see you again -----");
                }
            }
        }
        if (itemNumber > (this.softDrinkList().size()) || itemNumber <= 0) {
            System.out.println("ERROR: Invalid number. Please re-enter for Soft Drink Menu.");
            this.showChooseItem();
        }
    }

    public void addItem() {
        Scanner scannerName = new Scanner(System.in);
        Scanner scannerDescription = new Scanner(System.in);
        Scanner scannerPrice = new Scanner(System.in);

        boolean bError = true;

        System.out.println("+------------------ Add Soft Drink Menu -------------------+");

        try {
            System.out.print("Enter soft drink name: ");
            String softDrinkName = scannerName.nextLine();

            if (hasNameExist(softDrinkName)) {
                while (bError) {
                    System.out.println("ERROR: Soft drink name already exists. Please try again.");
                    System.out.print("Enter soft drink name: ");
                    softDrinkName = scannerName.nextLine();

                    if (!hasNameExist(softDrinkName)) {
                        bError = false;
                    }
                }
            }

            System.out.print("Enter soft drink description: ");
            String softDrinkDescription = scannerDescription.nextLine();
            System.out.print("Enter soft drink price: ");
            double softDrinkPrice = scannerPrice.nextDouble();

            List<SoftDrink> softDrinkList = this.softDrinkList();
            softDrinkList.add(new SoftDrink(softDrinkName, softDrinkDescription, softDrinkPrice));
            writeFile(softDrinkList);

            System.out.println("New soft drinks have been added.");
        } catch (Exception e) {
            System.out.println("ERROR: Invalid input data. Please try again");
            this.addItem();
        }
    }

    public void showMenu() {
        System.out.format("+-------------------- Choose Soft Drink Menu ---------------------+%n");
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        System.out.format("| # | Name       | Description                        | Price     |%n");
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        int i = 1;
        for (SoftDrink m : this.softDrinkList()) {
            System.out.format("| %s |%-12s| %-35s| %-10s| %n", i++, m.getName(), m.getDescription(), m.getPrice());
        }
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        System.out.format("| Enter 'back'   | Back to drink menu                             |%n");
        System.out.format("| Enter 'exit'   | Exit program                                   |%n");
        System.out.format("+----------------+------------------------------------------------+%n");
    }

    public void showChooseItem() {
        DrinkMenuBusiness drinkMenuBusiness = new DrinkMenuBusiness();

        System.out.print("Choose soft drink menu by number or 'back' or 'exit': ");
        Scanner scannerChooseItem = new Scanner(System.in);
        try {
            int chooseMenu = scannerChooseItem.nextInt();
            this.chooseItem(chooseMenu);
        } catch (Exception e) {
            String chooseMenu = scannerChooseItem.nextLine();
            if (chooseMenu.equalsIgnoreCase("back")) {
                drinkMenuBusiness.showMenu();
                drinkMenuBusiness.chooseItem();
            } else if (chooseMenu.equalsIgnoreCase("exit")) {
                System.exit(0);
            } else {
                System.out.println("Invalid input data. Please try again");
                this.showChooseItem();
            }
        }
    }

    public void updateItem() {
        System.out.println("+------------------ Update Soft Drink Menu -------------------+");

        Scanner scannerName = new Scanner(System.in);
        System.out.print("Enter soft drink name for update: ");
        String softDrinkName = scannerName.nextLine();

        if (hasNameExist(softDrinkName)) {
            Scanner scannerUpdateName = new Scanner(System.in);
            Scanner scannerUpdateDescription = new Scanner(System.in);
            Scanner scannerUpdatePrice = new Scanner(System.in);

            System.out.println("+-------------- Enter Information Soft Drink ---------------+");
            System.out.print("Enter soft drink name or 'next': ");
            String updateName = scannerUpdateName.nextLine();

            System.out.print("Enter soft drink description or 'next': ");
            String updateDescription = scannerUpdateDescription.nextLine();
            System.out.print("Enter soft drink price or 'next': ");
            String updatePrice = scannerUpdatePrice.nextLine();

            if (hasNameExist(updateName)) {
                boolean cError = true;
                while (cError) {
                    System.out.println("ERROR: Soft drink name already exists. Please try again.");
                    System.out.print("Enter soft drink name or 'next': ");
                    updateName = scannerUpdateName.nextLine();

                    if (!hasNameExist(updateName) || updateName.equalsIgnoreCase("next")) {
                        cError = false;
                    }
                }
            }

            List<SoftDrink> softDrinks = this.softDrinkList();
            for (SoftDrink s : softDrinks) {
                if (softDrinkName.equalsIgnoreCase(s.getName())) {
                    String newName = !updateName.equalsIgnoreCase("next") ? updateName : s.getName();
                    String newDescription = !updateDescription.equalsIgnoreCase("next") ? updateDescription : s.getDescription();

                    s.setName(newName);
                    s.setDescription(newDescription);
                    if (updatePrice.equalsIgnoreCase("next")) {
                        s.setPrice(s.getPrice());
                    } else if (isNumeric(updatePrice)) {
                        s.setPrice(Double.parseDouble(updatePrice));
                    } else {
                        System.out.println("ERROR: Invalid input data. Please try again.");
                    }
                }
            }
            writeFile(softDrinks);
            System.out.println("Soft drink has been updated.");
        } else {
            System.out.println("ERROR: Soft drink name not found. Please try again");
            this.updateItem();
        }
    }

    public void removeItem() {
        Scanner scannerName = new Scanner(System.in);
        List<SoftDrink> softDrinkList = this.softDrinkList();

        System.out.println("+-------------------- Delete Soft Drink Menu ---------------------+");

        System.out.print("Enter soft drink name: ");
        String softDrinkName = scannerName.nextLine();

        if (hasNameExist(softDrinkName)) {
            softDrinkList.removeIf(s -> s.getName().equals(softDrinkName));
            writeFile(softDrinkList);
            System.out.println("Soft drink has been deleted.");
        } else {
            System.out.println("Soft drink is not found. Please try again");
            this.removeItem();
        }
    }

    public void showManagement() {
        System.out.format("+--------------- Soft Drink Management ------------+%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| # | Function    | Description                    |%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| 1 | Add item    | Add item to soft drink menu    |%n");
        System.out.format("| 2 | Update item | Update item to soft drink menu |%n");
        System.out.format("| 3 | Delete item | Delete item to soft drink menu |%n");
        System.out.format("| 4 | Show item   | Show item of soft drink        |%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| Enter 'back'    | Back to drink management       |%n");
        System.out.format("| Enter 'exit'    | Exit program                   |%n");
        System.out.format("+-----------------+--------------------------------+%n");
    }

    public void chooseFunction() {
        DrinkMenuBusiness drinkMenuBusiness = new DrinkMenuBusiness();

        Scanner scannerFunction = new Scanner(System.in);

        System.out.print("Enter the function by number: ");
        try {
            int choose = scannerFunction.nextInt();
            final int ADD_DRINK = 1;
            final int UPDATE_DRINK = 2;
            final int REMOVE_DRINK = 3;
            final int SHOW_DRINKS = 4;

            switch (choose) {
                case ADD_DRINK -> {
                    this.addItem();
                    this.showManagement();
                    this.chooseFunction();
                }
                case UPDATE_DRINK -> {
                    this.updateItem();
                    this.showManagement();
                    this.chooseFunction();
                }
                case REMOVE_DRINK -> {
                    this.removeItem();
                    this.showManagement();
                    this.chooseFunction();
                }
                case SHOW_DRINKS -> {
                    softDrinkList().forEach(System.out::println);
                    this.showManagement();
                    this.chooseFunction();
                }
                default -> {
                    System.out.println("Invalid number. Please try again");
                    this.chooseFunction();
                }
            }
        } catch (Exception e) {
            String choose = scannerFunction.nextLine();
            if (choose.equalsIgnoreCase("back")) {
                drinkMenuBusiness.showManagement();
                drinkMenuBusiness.chooseFunction();
            } else if (choose.equalsIgnoreCase("exit")) {
                System.exit(0);
            } else {
                System.out.println("Invalid input data. Please try again");
                this.chooseFunction();
            }
        }
    }
}