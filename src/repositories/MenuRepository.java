package repositories;

import models.*;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MenuRepository {

    /**
     * Create a list with menu file
     *
     * @param fileName: file name
     * @return list of menu file
     */
    private static List<MenuModel> getListFromFile(String fileName) {
        List<String> intermediateList = new ArrayList<>();
        List<MenuModel> menuItems = new ArrayList<>();
        File file = new File("MenuData/" + fileName);
        try (Scanner scFile = new Scanner(file)) {
            int i = 0;
            while (scFile.hasNext()) {
                intermediateList.add(scFile.nextLine());
                String[] information = intermediateList.get(i++).split(", ");
                String name = information[0];
                String description = information[1];
                double price = Double.parseDouble(information[2]);
                TypeMenu type = TypeMenu.valueOf(information[3]);

                menuItems.add(new MenuModel(name, description, price, type));
            }
            return menuItems;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Rewrite menu file
     *
     * @param pathFile: path file
     * @param listName: object of menu
     */
    private void rewriteFile(File pathFile, List<MenuModel> listName) {
        try (BufferedWriter bw = Files.newBufferedWriter(pathFile.toPath())) {
            for (MenuModel m : listName) {
                bw.write(m.getName() + ", " + m.getDescription() + ", " + m.getPrice() + ", " + m.getType() + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get menu list from menu file
     *
     * @param fileName: file name
     * @return list of menu file
     */
    public List<MenuModel> getMenuList(String fileName) {
        return getListFromFile(fileName);
    }

    /**
     * Create a list with all menu
     *
     * @return list of all menu
     */
    public List<MenuModel> getAllMenus() {
        List<MenuModel> menus = new ArrayList<>();
        List<MenuModel> softDrinks = this.getMenuList("SoftDrink.txt");
        List<MenuModel> alcohols = this.getMenuList("Alcohol.txt");
        List<MenuModel> breakfasts = this.getMenuList("Breakfast.txt");
        List<MenuModel> lunches = this.getMenuList("Lunch.txt");
        List<MenuModel> dinners = this.getMenuList("Dinner.txt");

        menus.addAll(softDrinks);
        menus.addAll(alcohols);
        menus.addAll(breakfasts);
        menus.addAll(lunches);
        menus.addAll(dinners);

        return menus;
    }

    /**
     * Find item name in menu file
     *
     * @param fileName: file name
     * @param itemName: item name
     * @return true if item name exist in menu
     */
    public boolean hasItemName(String fileName, String itemName) {
        for (MenuModel m : getListFromFile(fileName)) {
            if (itemName.equals(m.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add an item at menu file
     *
     * @param fileName: file name
     * @param menuItem: menu item
     */
    public void addItem(String fileName, MenuModel menuItem) {
        File file = new File("MenuData/" + fileName);
        try (FileWriter fileWriter = new FileWriter(file, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            String name = menuItem.getName();
            String description = menuItem.getDescription();
            String price = String.valueOf(menuItem.getPrice());
            String type = String.valueOf(menuItem.getType());
            printWriter.println(name + ", " + description + ", " + price + ", " + type);
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

    /**
     * Update item for menu file
     *
     * @param fileName:       file name
     * @param nameWantUpdate: name want update
     * @param menuItem:       object of menu
     */
    public void updateItem(String fileName, String nameWantUpdate, MenuModel menuItem) {
        File file = new File("MenuData/" + fileName);
        List<MenuModel> menuItems = this.getMenuList(fileName);
        for (MenuModel m : menuItems) {
            if (m.getName().equals(nameWantUpdate)) {
                m.setName(menuItem.getName());
                m.setDescription(menuItem.getDescription());
                m.setPrice(menuItem.getPrice());
                break;
            }
        }

        this.rewriteFile(file, menuItems);
    }

    /**
     * Remove item for menu file
     *
     * @param fileName:      file name
     * @param nameWanRemove: name want remove
     */
    public void removeItem(String fileName, String nameWanRemove) {
        File file = new File("MenuData/" + fileName);
        List<MenuModel> menuItems = this.getMenuList(fileName);
        menuItems.removeIf(m -> m.getName().equals(nameWanRemove));

        this.rewriteFile(file, menuItems);
    }
}
