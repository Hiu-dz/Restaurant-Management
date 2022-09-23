package business.logic.layer;

import data.access.layer.Breakfast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BreakfastMenuBusiness {
    private final Path path = Paths.get("data\\menu\\Breakfast.txt");

    public Path getPath() {
        return path;
    }

    private List<Breakfast> breakfastMenus() {
        List<String> strings = new ArrayList<>();
        List<Breakfast> breakfastMenuList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(getPath().toUri()))) {
            int i = 0;
            while (scanner.hasNext()) {
                strings.add(scanner.nextLine());
                String[] words = strings.get(i++).split(", ");
                breakfastMenuList.add(new Breakfast(words[0], words[1], Double.parseDouble(words[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return breakfastMenuList;
    }

    private void writeFile(List<Breakfast> listName) {
        try (BufferedWriter bw = Files.newBufferedWriter(getPath())) {
            for (Breakfast b : listName) {
                bw.write(b.getName() + ", " + b.getDescription() + ", " + b.getPrice() + "\n");
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public boolean hasNameExist(String itemName) {
        return this.breakfastMenus().stream().anyMatch(n -> itemName.equals(n.getName()));
    }

    protected boolean hasName(String itemName) {
        return this.breakfastMenus().stream().anyMatch(n -> itemName.equalsIgnoreCase(n.getName()));
    }

    protected boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        return str.matches("\\d+");
    }

    protected void chooseItem(int chooseItemNumber) {
        MenuBusiness menuBusiness = new MenuBusiness();
        BillManagementBusiness billManagementBusiness = new BillManagementBusiness();

        for (int i = 0; i < this.breakfastMenus().size(); i++) {
            int itemNumber = i + 1;
            if (chooseItemNumber == itemNumber) {
                Scanner scannerQuantity = new Scanner(System.in);
                Scanner scannerChooseContinue = new Scanner(System.in);

                System.out.print("Enter " + this.breakfastMenus().get(i).getName() + " quantity: ");
                String strQuantity = scannerQuantity.nextLine();
                while (!isNumeric(strQuantity)) {
                    System.out.println("ERROR: Invalid input data. Please try again");
                    System.out.print("Enter " + this.breakfastMenus().get(i).getName() + " quantity: ");
                    strQuantity = scannerQuantity.nextLine();
                }
                String menuItem = this.breakfastMenus().get(i).getName();
                int quantity = Integer.parseInt(strQuantity);
                double priceItems = this.breakfastMenus().get(i).getPrice();
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
        if (chooseItemNumber > (this.breakfastMenus().size()) || chooseItemNumber <= 0) {
            System.out.println("ERROR: Invalid number. Please re-enter for Breakfast Menu.");
            this.showChooseItem();
        }
    }

    public void addItem() {
        Scanner scannerName = new Scanner(System.in);
        Scanner scannerDescription = new Scanner(System.in);
        Scanner scannerPrice = new Scanner(System.in);

        boolean bError = true;

        System.out.println("+------------------ Add Breakfast Menu -------------------+");

        try {
            System.out.print("Enter breakfast name: ");
            String name = scannerName.nextLine();

            if (hasNameExist(name)) {
                while (bError) {
                    System.out.println("ERROR: Breakfast name already exists. Please try again.");
                    System.out.print("Enter breakfast name: ");
                    name = scannerName.nextLine();

                    if (!hasNameExist(name)) {
                        bError = false;
                    }
                }
            }

            System.out.print("Enter breakfast description: ");
            String description = scannerDescription.nextLine();
            System.out.print("Enter breakfast price: ");
            double price = scannerPrice.nextDouble();

            List<Breakfast> breakfastMenus = this.breakfastMenus();
            breakfastMenus.add(new Breakfast(name, description, price));
            writeFile(breakfastMenus);

            System.out.println("New breakfast have been added.");
        } catch (Exception e) {
            System.out.println("ERROR: Invalid input data. Please try again");
            this.addItem();
        }
    }

    public void showMenu() {
        System.out.format("+--------------------- Choose Breakfast Menu ---------------------+%n");
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        System.out.format("| # | Name       | Description                        | Price     |%n");
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        int i = 1;
        for (Breakfast m : this.breakfastMenus()) {
            System.out.format("| %s |%-12s| %-35s| %-10s| %n", i++, m.getName(), m.getDescription(), m.getPrice());
        }
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        System.out.format("| Enter 'back'   | Back to food menu                              |%n");
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
            final String BACK_FOOD_MANAGEMENT = "back";
            final String EXIT_PROGRAM = "exit";

            if (chooseMenu.equalsIgnoreCase(BACK_FOOD_MANAGEMENT)) {
                drinkMenuBusiness.showMenu();
                drinkMenuBusiness.chooseItem();
            } else if (chooseMenu.equalsIgnoreCase(EXIT_PROGRAM)) {
                System.exit(0);
            } else {
                System.out.println("Invalid input data. Please try again");
                this.showChooseItem();
            }
        }
    }

    public void updateItem() {
        System.out.println("+------------------ Update Breakfast Menu -------------------+");

        Scanner scannerName = new Scanner(System.in);
        System.out.print("Enter breakfast name for update: ");
        String name = scannerName.nextLine();

        if (hasNameExist(name)) {
            Scanner scannerUpdateName = new Scanner(System.in);
            Scanner scannerUpdateDescription = new Scanner(System.in);
            Scanner scannerUpdatePrice = new Scanner(System.in);

            System.out.println("+-------------- Enter Information Breakfast ---------------+");
            System.out.print("Enter breakfast name or 'next': ");
            String updateName = scannerUpdateName.nextLine();

            System.out.print("Enter breakfast description or 'next': ");
            String updateDescription = scannerUpdateDescription.nextLine();
            System.out.print("Enter breakfast price or 'next': ");
            String updatePrice = scannerUpdatePrice.nextLine();

            if (hasNameExist(updateName)) {
                boolean cError = true;
                while (cError) {
                    System.out.println("ERROR: Breakfast name already exists. Please try again.");
                    System.out.print("Enter breakfast name or 'next': ");
                    updateName = scannerUpdateName.nextLine();

                    if (!hasNameExist(updateName) || updateName.equalsIgnoreCase("next")) {
                        cError = false;
                    }
                }
            }

            List<Breakfast> breakfastMenus = this.breakfastMenus();
            for (Breakfast s : breakfastMenus) {
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
            this.writeFile(breakfastMenus);
            System.out.println("Breakfast has been updated.");
        } else {
            System.out.println("ERROR: Breakfast name not found. Please try again");
            this.updateItem();
        }
    }

    public void removeItem() {
        Scanner scannerName = new Scanner(System.in);
        List<Breakfast> breakfastMenus = this.breakfastMenus();

        System.out.println("+-------------------- Delete Breakfast Menu ---------------------+");

        System.out.print("Enter breakfast name: ");
        String name = scannerName.nextLine();

        if (hasNameExist(name)) {
            breakfastMenus.removeIf(s -> s.getName().equals(name));
            writeFile(breakfastMenus);
            System.out.println("Breakfast has been deleted");
        } else {
            System.out.println("Breakfast is not found. Please try again");
        }
    }

    public void showManagement() {
        System.out.format("+--------------- Breakfast Management ------------+%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| # | Function    | Description                    |%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| 1 | Add item    | Add item to breakfast menu     |%n");
        System.out.format("| 2 | Update item | Update item to breakfast menu  |%n");
        System.out.format("| 3 | Delete item | Delete item to breakfast menu  |%n");
        System.out.format("| 4 | Show item   | Show item of breakfast         |%n");
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
                    breakfastMenus().forEach(System.out::println);
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
