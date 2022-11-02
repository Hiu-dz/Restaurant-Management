package repositories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.*;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MenuRepository {

    /**
     * Create a list with menu file
     *
     * @param fileName: file name
     * @return list of menu file
     */
    public static List<MenuModel> getListFromFile(String fileName) {
        Gson gson = new Gson();

        try {
            Path path = Paths.get("MenuData/" + fileName);
            Reader reader = Files.newBufferedReader(path);
            List<MenuModel> menuItems = gson.fromJson(reader, new TypeToken<List<MenuModel>>() {}.getType());
            if (menuItems != null) {
                return menuItems;
            }
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Rewrite menu file
     *
     * @param pathFile: path file
     * @param listName: object of menu
     */
    private void rewriteFile(File pathFile, List<MenuModel> listName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            Writer writer = Files.newBufferedWriter(pathFile.toPath());
            gson.toJson(listName, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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
    public List<MenuModel> getAllMenuItems() {
        List<MenuModel> menuItems = new ArrayList<>();
        List<MenuModel> softDrinks = this.getMenuList(TypeMenu.SOFT_DRINK.getFile());
        List<MenuModel> alcohols = this.getMenuList(TypeMenu.ALCOHOL.getFile());
        List<MenuModel> breakfasts = this.getMenuList(TypeMenu.BREAKFAST.getFile());
        List<MenuModel> lunches = this.getMenuList(TypeMenu.LUNCH.getFile());
        List<MenuModel> dinners = this.getMenuList(TypeMenu.DINNER.getFile());

        menuItems.addAll(softDrinks);
        menuItems.addAll(alcohols);
        menuItems.addAll(breakfasts);
        menuItems.addAll(lunches);
        menuItems.addAll(dinners);

        return menuItems;
    }

    /**
     * Find item name in menu file
     *
     * @param fileName: file name
     * @param itemName: item name
     * @return true if item name exist in menu
     */
    public boolean hasItemName(String fileName, String itemName) {
        if (!getListFromFile(fileName).isEmpty()) {
            for (MenuModel m : getListFromFile(fileName)) {
                if (itemName.equals(m.getName())) {
                    return true;
                }
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
        List<MenuModel> menuItems = getListFromFile(fileName);
        menuItems.add(menuItem);

        this.rewriteFile(file, menuItems);
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
