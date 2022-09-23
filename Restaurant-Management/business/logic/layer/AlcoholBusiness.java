package business.logic.layer;

import data.access.layer.Alcohol;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class AlcoholBusiness {
    private final Path path = Paths.get("data\\menu\\Alcohol.txt");

    public Path getPath() {
        return path;
    }

    private List<Alcohol> alcoholList() {
        List<String> strings = new ArrayList<>();
        List<Alcohol> alcohols = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(getPath().toUri()))) {
            int i = 0;
            while (scanner.hasNext()) {
                strings.add(scanner.nextLine());
                String[] words = strings.get(i++).split(", ");
                alcohols.add(new Alcohol(words[0], words[1], Double.parseDouble(words[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return alcohols;
    }

    private void writeFile(List<Alcohol> listName) {
        try (BufferedWriter bw = Files.newBufferedWriter(getPath())) {
            for (Alcohol a : listName) {
                bw.write(a.getName() + ", " + a.getDescription() + ", " + a.getPrice() + "\n");
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public boolean hasNameExist(String itemName) {
        return this.alcoholList().stream().anyMatch(n -> itemName.equals(n.getName()));
    }

    protected boolean hasName(String itemName) {
        return this.alcoholList().stream().anyMatch(n -> itemName.equalsIgnoreCase(n.getName()));
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

        for (int i = 0; i < this.alcoholList().size(); i++) {
            if (itemNumber == i + 1) {
                Scanner scannerQuantity = new Scanner(System.in);
                Scanner scannerChooseContinue = new Scanner(System.in);

                System.out.print("Enter " + this.alcoholList().get(i).getName() + " quantity: ");
                String strQuantity = scannerQuantity.nextLine();
                while (!isNumeric(strQuantity)) {
                    System.out.println("ERROR: Invalid input data. Please try again");
                    System.out.print("Enter " + this.alcoholList().get(i).getName() + " quantity: ");
                    strQuantity = scannerQuantity.nextLine();
                }
                String menuItem = this.alcoholList().get(i).getName();
                int quantity = Integer.parseInt(strQuantity);
                double priceItems = this.alcoholList().get(i).getPrice();
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
        if (itemNumber > (this.alcoholList().size()) || itemNumber <= 0) {
            System.out.println("ERROR: Invalid number. Please re-enter for Soft Drink Menu.");
            this.showChooseItem();
        }
    }

    public void addItem() {
        Scanner scannerName = new Scanner(System.in);
        Scanner scannerDescription = new Scanner(System.in);
        Scanner scannerPrice = new Scanner(System.in);

        boolean bError = true;

        System.out.println("+------------------ Add Alcohol Menu -------------------+");

        try {
            System.out.print("Enter alcohol name: ");
            String alcoholName = scannerName.nextLine();

            if (hasNameExist(alcoholName)) {
                while (bError) {
                    System.out.println("ERROR: Alcohol name already exists. Please try again.");
                    System.out.print("Enter alcohol name: ");
                    alcoholName = scannerName.nextLine();

                    if (!hasNameExist(alcoholName)) {
                        bError = false;
                    }
                }
            }

            System.out.print("Enter alcohol description: ");
            String alcoholDescription = scannerDescription.nextLine();
            System.out.print("Enter alcohol price: ");
            double alcoholPrice = scannerPrice.nextDouble();

            List<Alcohol> softDrinkList = this.alcoholList();
            softDrinkList.add(new Alcohol(alcoholName, alcoholDescription, alcoholPrice));
            writeFile(softDrinkList);

            System.out.println("New alcohol have been added.");
        } catch (Exception e) {
            System.out.println("ERROR: Invalid input data. Please try again");
            this.addItem();
        }
    }

    public void showMenu() {
        System.out.format("+---------------------- Choose Alcohol Menu ----------------------+%n");
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        System.out.format("| # | Name       | Description                        | Price     |%n");
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        int i = 1;
        for (Alcohol m : this.alcoholList()) {
            System.out.format("| %s |%-12s| %-35s| %-10s| %n", i++, m.getName(), m.getDescription(), m.getPrice());
        }
        System.out.format("+---+------------+------------------------------------+-----------+%n");
        System.out.format("| Enter 'back'   | Back to drink menu                             |%n");
        System.out.format("| Enter 'exit'   | Exit program                                   |%n");
        System.out.format("+----------------+------------------------------------------------+%n");
    }

    public void showChooseItem() {
        DrinkMenuBusiness drinkMenuBusiness = new DrinkMenuBusiness();

        System.out.print("Choose alcohol menu by number or 'back' or 'exit': ");
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
        System.out.println("+------------------ Update Alcohol Menu -------------------+");

        Scanner scannerName = new Scanner(System.in);
        System.out.print("Enter alcohol name for update: ");
        String softDrinkName = scannerName.nextLine();

        if (hasName(softDrinkName)) {
            Scanner scannerUpdateName = new Scanner(System.in);
            Scanner scannerUpdateDescription = new Scanner(System.in);
            Scanner scannerUpdatePrice = new Scanner(System.in);

            System.out.println("+-------------- Enter Information Alcohol ---------------+");
            System.out.print("Enter alcohol name or 'next': ");
            String updateName = scannerUpdateName.nextLine();

            System.out.print("Enter alcohol description or 'next': ");
            String updateDescription = scannerUpdateDescription.nextLine();
            System.out.print("Enter alcohol price or 'next': ");
            String updatePrice = scannerUpdatePrice.nextLine();

            if (hasNameExist(updateName)) {
                boolean cError = true;
                while (cError) {
                    System.out.println("ERROR: Alcohol name already exists. Please try again.");
                    System.out.print("Enter alcohol name or 'next': ");
                    updateName = scannerUpdateName.nextLine();

                    if (!hasNameExist(updateName) || updateName.equalsIgnoreCase("next")) {
                        cError = false;
                    }
                }
            }

            List<Alcohol> alcohols = this.alcoholList();
            for (Alcohol a : alcohols) {
                if (softDrinkName.equalsIgnoreCase(a.getName())) {
                    a.setName(!updateName.equalsIgnoreCase("next") ? updateName : a.getName());
                    a.setDescription(!updateDescription.equalsIgnoreCase("next") ? updateDescription : a.getDescription());
                    if (updatePrice.equalsIgnoreCase("next")) {
                        a.setPrice(a.getPrice());
                    } else if (isNumeric(updatePrice)) {
                        a.setPrice(Double.parseDouble(updatePrice));
                    } else {
                        System.out.println("ERROR: Invalid input data. Please try again.");
                    }
                }
            }
            writeFile(alcohols);
            System.out.println("Alcohol has been updated.");
        } else {
            System.out.println("ERROR: Alcohol name not found. Please try again");
            this.updateItem();
        }
    }

    public void removeItem() {
        Scanner scannerName = new Scanner(System.in);
        List<Alcohol> alcohols = this.alcoholList();

        System.out.println("+-------------------- Delete Alcohol Menu ---------------------+");

        System.out.print("Enter alcohol name: ");
        String alcoholName = scannerName.nextLine();

        if (hasNameExist(alcoholName)) {
            alcohols.removeIf(s -> s.getName().equals(alcoholName));
            writeFile(alcohols);
            System.out.println("Alcohol has been deleted");
        } else {
            System.out.println("Alcohol is not found. Please try again");
        }
    }

    public void showManagement() {
        System.out.format("+--------------- Alcohol Management ------------+%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| # | Function    | Description                    |%n");
        System.out.format("+---+-------------+--------------------------------+%n");
        System.out.format("| 1 | Add item    | Add item to alcohol menu       |%n");
        System.out.format("| 2 | Update item | Update item to alcohol menu    |%n");
        System.out.format("| 3 | Delete item | Delete item to alcohol menu    |%n");
        System.out.format("| 4 | Show item   | Show item of alcohol menu      |%n");
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
                    alcoholList().forEach(System.out::println);
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
