package business.logic.layer;

import data.access.layer.LunchMenu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LunchMenuBusiness {
    private final Path path = Paths.get("data\\menu\\Lunch.txt");

    public Path getPath() {
        return path;
    }

    private List<LunchMenu> lunchMenus() {
        List<String> strings = new ArrayList<>();
        List<LunchMenu> lunches = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(getPath().toUri()))) {
            int i = 0;
            while (scanner.hasNext()) {
                strings.add(scanner.nextLine());
                String[] words = strings.get(i++).split(", ");
                lunches.add(new LunchMenu(words[0], words[1], Double.parseDouble(words[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lunches;
    }

    private void writeFile(List<LunchMenu> listName) {
        try (BufferedWriter bw = Files.newBufferedWriter(getPath())) {
            for (LunchMenu b : listName) {
                bw.write(b.getName() + ", " + b.getDescription() + ", " + b.getPrice() + "\n");
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    protected boolean hasNameExist(String itemName) {
        return this.lunchMenus().stream().anyMatch(n -> itemName.equals(n.getName()));
    }

    protected boolean hasName(String itemName) {
        return this.lunchMenus().stream().anyMatch(n -> itemName.equalsIgnoreCase(n.getName()));
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

        for (int i = 0; i < this.lunchMenus().size(); i++) {
            if (itemNumber == i + 1) {
                Scanner scannerQuantity = new Scanner(System.in);
                Scanner scannerChooseContinue = new Scanner(System.in);

                System.out.print("Enter " + this.lunchMenus().get(i).getName() + " quantity: ");
                String strQuantity = scannerQuantity.nextLine();
                while (!isNumeric(strQuantity)) {
                    System.out.println("ERROR: Invalid input data. Please try again");
                    System.out.print("Enter " + this.lunchMenus().get(i).getName() + " quantity: ");
                    strQuantity = scannerQuantity.nextLine();
                }
                String menuItem = this.lunchMenus().get(i).getName();
                int quantity = Integer.parseInt(strQuantity);
                double priceItems = this.lunchMenus().get(i).getPrice();
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
        if (itemNumber > (this.lunchMenus().size()) || itemNumber <= 0) {
            System.out.println("ERROR: Invalid number. Please re-enter for Breakfast Menu.");
            this.showChooseItem();
        }
    }

    public void addItem() {
        Scanner scannerName = new Scanner(System.in);
        Scanner scannerDescription = new Scanner(System.in);
        Scanner scannerPrice = new Scanner(System.in);

        boolean bError = true;

        System.out.println("+------------------ Add Lunch Menu -------------------+");

        try {
            System.out.print("Enter breakfast name: ");
            String name = scannerName.nextLine();

            if (hasNameExist(name)) {
                while (bError) {
                    System.out.println("ERROR: Lunch name already exists. Please try again.");
                    System.out.print("Enter lunch name: ");
                    name = scannerName.nextLine();

                    if (!hasNameExist(name)) {
                        bError = false;
                    }
                }
            }

            System.out.print("Enter lunch description: ");
            String description = scannerDescription.nextLine();
            System.out.print("Enter lunch price: ");
            double price = scannerPrice.nextDouble();

            List<LunchMenu> lunches = this.lunchMenus();
            lunches.add(new LunchMenu(name, description, price));
            writeFile(lunches);

            System.out.println("New lunch have been added.");
        } catch (Exception e) {
            System.out.println("ERROR: Invalid input data. Please try again");
            this.addItem();
        }
    }

    public void showMenu() {
        System.out.format("+----------------------- Choose Lunch Menu -----------------------+%n");
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        System.out.format("| # | Name       | Description                        | Price     |%n");
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        int i = 1;
        for (LunchMenu m : this.lunchMenus()) {
            System.out.format("| %s |%-12s| %-35s| %-10s| %n", i++, m.getName(), m.getDescription(), m.getPrice());
        }
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        System.out.format("| Enter 'back'   | Back to food menu                              |%n");
        System.out.format("| Enter 'exit'   | Exit program                                   |%n");
        System.out.format("+----------------+------------------------------------------------+%n");
    }

    public void showChooseItem() {
        FoodMenuBusiness foodMenuBusiness = new FoodMenuBusiness();

        System.out.print("Choose lunch menu by number or 'back' or 'exit': ");
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
        System.out.println("+------------------ Update Lunch Menu -------------------+");

        Scanner scannerName = new Scanner(System.in);
        System.out.print("Enter lunch name for update: ");
        String name = scannerName.nextLine();

        if (hasNameExist(name)) {
            Scanner scannerUpdateName = new Scanner(System.in);
            Scanner scannerUpdateDescription = new Scanner(System.in);
            Scanner scannerUpdatePrice = new Scanner(System.in);

            System.out.println("+-------------- Enter Information Lunch ---------------+");
            System.out.print("Enter lunch name or 'next': ");
            String updateName = scannerUpdateName.nextLine();

            System.out.print("Enter lunch description or 'next': ");
            String updateDescription = scannerUpdateDescription.nextLine();
            System.out.print("Enter lunch price or 'next': ");
            String updatePrice = scannerUpdatePrice.nextLine();

            if (hasNameExist(updateName)) {
                boolean cError = true;
                while (cError) {
                    System.out.println("ERROR: Lunch name already exists. Please try again.");
                    System.out.print("Enter lunch name or 'next': ");
                    updateName = scannerUpdateName.nextLine();

                    if (!hasNameExist(updateName) || updateName.equalsIgnoreCase("next")) {
                        cError = false;
                    }
                }
            }

            List<LunchMenu> lunches = this.lunchMenus();
            for (LunchMenu s : lunches) {
                if (name.equalsIgnoreCase(s.getName())) {
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
            writeFile(lunches);
            System.out.println("Lunch has been updated.");
        } else {
            System.out.println("ERROR: Lunch name not found. Please try again");
            this.updateItem();
        }
    }

    public void removeItem() {
        Scanner scannerName = new Scanner(System.in);
        List<LunchMenu> lunches = this.lunchMenus();

        System.out.println("+-------------------- Delete Lunch Menu ---------------------+");

        System.out.print("Enter lunch name: ");
        String name = scannerName.nextLine();

        if (hasNameExist(name)) {
            lunches.removeIf(s -> s.getName().equals(name));
            writeFile(lunches);
            System.out.println("Lunch has been deleted");
        } else {
            System.out.println("Lunch is not found. Please try again");
        }
    }

    public void showManagement() {
        System.out.format("+------------------ Lunch Management --------------+%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| # | Function    | Description                    |%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| 1 | Add item    | Add item to lunch menu         |%n");
        System.out.format("| 2 | Update item | Update item to lunch menu      |%n");
        System.out.format("| 3 | Delete item | Delete item to lunch menu      |%n");
        System.out.format("| 4 | Show item   | Show item of lunch             |%n");
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
            switch (choose) {
                case 1 -> {
                    this.addItem();
                    this.showManagement();
                    this.chooseFunction();
                }
                case 2 -> {
                    this.updateItem();
                    this.showManagement();
                    this.chooseFunction();
                }
                case 3 -> {
                    this.removeItem();
                    this.showManagement();
                    this.chooseFunction();
                }
                case 4 -> {
                    lunchMenus().forEach(System.out::println);
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
