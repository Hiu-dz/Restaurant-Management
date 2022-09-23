package business.logic.layer;

import data.access.layer.DinnerMenu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DinnerMenuBusiness{
    private final Path path = Paths.get("data\\menu\\Dinner.txt");

    public Path getPath() {
        return path;
    }

    private List<DinnerMenu> dinnerMenus() {
        List<String> strings = new ArrayList<>();
        List<DinnerMenu> dinners = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(getPath().toUri()))) {
            int i = 0;
            while (scanner.hasNext()) {
                strings.add(scanner.nextLine());
                String[] words = strings.get(i++).split(", ");
                dinners.add(new DinnerMenu(words[0], words[1], Double.parseDouble(words[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dinners;
    }

    private void writeFile(List<DinnerMenu> listName) {
        try (BufferedWriter bw = Files.newBufferedWriter(getPath())) {
            for (DinnerMenu b : listName) {
                bw.write(b.getName() + ", " + b.getDescription() + ", " + b.getPrice() + "\n");
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public boolean hasNameExist(String itemName) {
        return this.dinnerMenus().stream().anyMatch(n -> itemName.equals(n.getName()));
    }

    protected boolean hasName(String itemName) {
        return this.dinnerMenus().stream().anyMatch(n -> itemName.equalsIgnoreCase(n.getName()));
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

        for (int i = 0; i < this.dinnerMenus().size(); i++) {
            if (itemNumber == i + 1) {
                Scanner scannerQuantity = new Scanner(System.in);
                Scanner scannerChooseContinue = new Scanner(System.in);

                System.out.print("Enter " + this.dinnerMenus().get(i).getName() + " quantity: ");
                String strQuantity = scannerQuantity.nextLine();
                while (!isNumeric(strQuantity)) {
                    System.out.println("ERROR: Invalid input data. Please try again");
                    System.out.print("Enter " + this.dinnerMenus().get(i).getName() + " quantity: ");
                    strQuantity = scannerQuantity.nextLine();
                }
                String menuItem = this.dinnerMenus().get(i).getName();
                int quantity = Integer.parseInt(strQuantity);
                double priceItems = this.dinnerMenus().get(i).getPrice();
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
        if (itemNumber > (this.dinnerMenus().size()) || itemNumber <= 0) {
            System.out.println("ERROR: Invalid number. Please re-enter for Breakfast Menu.");
            this.showChooseItem();
        }
    }

    public void addItem() {
        Scanner scannerName = new Scanner(System.in);
        Scanner scannerDescription = new Scanner(System.in);
        Scanner scannerPrice = new Scanner(System.in);

        System.out.println("+------------------ Add Dinner Menu -------------------+");

        try {
            System.out.print("Enter dinner name: ");
            String name = scannerName.nextLine();

            if (hasNameExist(name)) {
                boolean bError = true;
                while (bError) {
                    System.out.println("ERROR: Dinner name already exists. Please try again.");
                    System.out.print("Enter dinner name: ");
                    name = scannerName.nextLine();

                    if (!hasNameExist(name)) {
                        bError = false;
                    }
                }
            }

            System.out.print("Enter dinner description: ");
            String description = scannerDescription.nextLine();
            System.out.print("Enter dinner price: ");
            double price = scannerPrice.nextDouble();

            List<DinnerMenu> dinners = this.dinnerMenus();
            dinners.add(new DinnerMenu(name, description, price));
            writeFile(dinners);

            System.out.println("New dinner have been added.");
        } catch (Exception e) {
            System.out.println("ERROR: Invalid input data. Please try again");
            this.addItem();
        }
    }

    public void showMenu() {
        System.out.format("+---------------------- Choose Dinner Menu -----------------------+%n");
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        System.out.format("| # | Name       | Description                        | Price     |%n");
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        int i = 1;
        for (DinnerMenu m : this.dinnerMenus()) {
            System.out.format("| %s |%-12s| %-35s| %-10s| %n", i++, m.getName(), m.getDescription(), m.getPrice());
        }
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        System.out.format("| Enter 'back'   | Back to food menu                              |%n");
        System.out.format("| Enter 'exit'   | Exit program                                   |%n");
        System.out.format("+----------------+------------------------------------------------+%n");
    }

    public void showChooseItem() {
        FoodMenuBusiness foodMenuBusiness = new FoodMenuBusiness();

        System.out.print("Choose dinner menu by number or 'back' or 'exit': ");
        Scanner scannerChooseItem = new Scanner(System.in);
        try {
            int chooseMenu = scannerChooseItem.nextInt();
            this.chooseItem(chooseMenu);
        } catch (Exception e) {
            String chooseMenu = scannerChooseItem.nextLine();
            if (chooseMenu.equalsIgnoreCase("back")) {
                foodMenuBusiness.showMenu();
                foodMenuBusiness.showChooseMenu();
            } else if (chooseMenu.equalsIgnoreCase("exit")) {
                System.exit(0);
            } else {
                System.out.println("Invalid input data. Please try again");
                this.showChooseItem();
            }
        }
    }

    public void updateItem() {
        System.out.println("+------------------ Update Dinner Menu -------------------+");

        Scanner scannerName = new Scanner(System.in);
        System.out.print("Enter dinner name for update: ");
        String name = scannerName.nextLine();

        if (hasNameExist(name)) {
            Scanner scannerUpdateName = new Scanner(System.in);
            Scanner scannerUpdateDescription = new Scanner(System.in);
            Scanner scannerUpdatePrice = new Scanner(System.in);

            System.out.println("+-------------- Enter Information Dinner ---------------+");
            System.out.print("Enter dinner name or 'next': ");
            String updateName = scannerUpdateName.nextLine();

            System.out.print("Enter dinner description or 'next': ");
            String updateDescription = scannerUpdateDescription.nextLine();
            System.out.print("Enter dinner price or 'next': ");
            String updatePrice = scannerUpdatePrice.nextLine();

            if (hasNameExist(updateName)) {
                boolean cError = true;
                while (cError) {
                    System.out.println("ERROR: Dinner name already exists. Please try again.");
                    System.out.print("Enter dinner name or 'next': ");
                    updateName = scannerUpdateName.nextLine();

                    if (!hasNameExist(updateName) || updateName.equalsIgnoreCase("next")) {
                        cError = false;
                    }
                }
            }

            List<DinnerMenu> dinners = this.dinnerMenus();
            for (DinnerMenu d : dinners) {
                if (name.equalsIgnoreCase(d.getName())) {
                    String newName = !updateName.equalsIgnoreCase("next") ? updateName : d.getName();
                    String newDescription = !updateDescription.equalsIgnoreCase("next") ? updateDescription : d.getDescription();

                    d.setName(newName);
                    d.setDescription(newDescription);
                    if (updatePrice.equalsIgnoreCase("next")) {
                        d.setPrice(d.getPrice());
                    } else if (isNumeric(updatePrice)) {
                        d.setPrice(Double.parseDouble(updatePrice));
                    } else {
                        System.out.println("ERROR: Invalid input data. Please try again.");
                    }
                }
            }
            writeFile(dinners);
            System.out.println("Dinner has been updated.");
        } else {
            System.out.println("ERROR: Dinner name not found. Please try again");
            this.updateItem();
        }
    }

    public void removeItem() {
        Scanner scannerName = new Scanner(System.in);
        List<DinnerMenu> dinners = this.dinnerMenus();

        System.out.println("+-------------------- Delete Dinner Menu ---------------------+");

        System.out.print("Enter dinner name: ");
        String name = scannerName.nextLine();

        if (hasNameExist(name)) {
            dinners.removeIf(s -> s.getName().equals(name));
            writeFile(dinners);
            System.out.println("Dinner has been deleted");
        } else {
            System.out.println("Dinner is not found. Please try again");
        }
    }

    public void showManagement() {
        System.out.format("+----------------- Dinner Management --------------+%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| # | Function    | Description                    |%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| 1 | Add item    | Add item to dinner menu        |%n");
        System.out.format("| 2 | Update item | Update item to dinner menu     |%n");
        System.out.format("| 3 | REMOVE item | Delete item to dinner menu     |%n");
        System.out.format("| 4 | Show item   | Show item of dinner            |%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| Enter 'back'    | Back to food management        |%n");
        System.out.format("| Enter 'exit'    | Exit program                   |%n");
        System.out.format("+-----------------+--------------------------------+%n");
    }

    public void chooseFunction() {
        FoodMenuBusiness foodMenuBusiness = new FoodMenuBusiness();

        Scanner scannerFunction = new Scanner(System.in);

        System.out.print("Enter the function by number: ");
        try {
            int choose = scannerFunction.nextInt();
            final int ADD_ITEM = 1;
            final int UPDATE_ITEM = 2;
            final int REMOVE_ITEM = 3;
            final int SHOW_ITEMS = 4;
            switch (choose) {
                case ADD_ITEM -> {
                    this.addItem();
                    this.showManagement();
                    this.chooseFunction();
                }
                case UPDATE_ITEM -> {
                    this.updateItem();
                    this.showManagement();
                    this.chooseFunction();
                }
                case REMOVE_ITEM -> {
                    this.removeItem();
                    this.showManagement();
                    this.chooseFunction();
                }
                case SHOW_ITEMS -> {
                    dinnerMenus().forEach(System.out::println);
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
                foodMenuBusiness.showManagement();
                foodMenuBusiness.chooseFunction();
            } else if (choose.equalsIgnoreCase("exit")) {
                System.exit(0);
            } else {
                System.out.println("Invalid input data. Please try again");
                this.chooseFunction();
            }
        }
    }
}
