package repositories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.BillModel;
import models.MenuModel;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class BillRepository {
    private final List<BillModel> billList;
    private final Map<MenuModel, Integer> menuItemsList;

    public BillRepository() {
        this.billList = new ArrayList<>();
        this.menuItemsList = new HashMap<>();
    }

    public Map<MenuModel, Integer> getMenuItemsList() {
        return menuItemsList;
    }

    /**
     * Create bill list from file og bill
     *
     * @param billFile : bill file
     * @return bill list
     */
    private static List<BillModel> getBillListFromFile(String billFile) {
        Gson gson = new Gson();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("BillData/" + billFile));
            Type type = new TypeToken<List<BillModel>>() {
            }.getType();
            return new ArrayList<>(gson.fromJson(br, type));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Rewrite bill file
     *
     * @param list:     bill list
     * @param billFile: path of bill file
     */
    private void rewriteFile(List<BillModel> list, String billFile) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            File file = new File("BillData/" + billFile);
            Writer writer = Files.newBufferedWriter(file.toPath());
            gson.toJson(list, writer);
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get all date bill in directory
     *
     * @return list of date bill
     */
    public List<String> getAllMonthBill() {
        File directoryPath = new File("BillData/");
        String[] contents = directoryPath.list();
        List<String> directories = new ArrayList<>();
        if (contents != null) {
            for (String content : contents) {
                File tempDirectory = new File("BillData/" + content);
                if (tempDirectory.isFile()) {
                    content = content.replace(".json", "");
                    directories.add(content);
                }
            }
        }
        return directories.stream().sorted(Collections.reverseOrder()).toList();
    }

    /**
     * Get all bill in month bill
     *
     * @param billFile: bill file
     * @return list of date bill
     */
    public List<BillModel> getAllBill(String billFile) {
        return getBillListFromFile(billFile);
    }

    /**
     * Get a bill in month bill
     *
     * @param monthBill: month of bill
     * @param index:     bill file
     * @return a bill
     */
    public BillModel getOneBill(String monthBill, int index) {
        List<BillModel> bills = getBillListFromFile(monthBill);
        return bills.get(index);
    }

    /**
     * Add menu item at bill list
     *
     * @param menuModel: obj of menu model
     * @param quantity:  quantity of menu item
     */
    public void addMenuItem(MenuModel menuModel, int quantity) {
        menuItemsList.put(menuModel, quantity);
    }

    /**
     * Add new bill to month bill
     *
     * @param map: menu items
     */
    public void createBill(Map<MenuModel, Integer> map) {
        LocalDate orderedTime = LocalDate.now();
        String billFile = orderedTime.format(DateTimeFormatter.ofPattern("MM-yyyy")) + ".json";
        billList.add(new BillModel(map, orderedTime));

        if (Files.exists(Path.of("BillData/" + billFile))) {
            List<BillModel> bills = getBillListFromFile(billFile);
            bills.addAll(billList);
            this.rewriteFile(bills, billFile);
        } else {
            this.rewriteFile(billList, billFile);
        }
    }

    /**
     * Get bill when order done
     *
     * @return bill
     */
    public BillModel getBillWhenOrderDone() {
        List<BillModel> bill = billList;
        int newBill = bill.size() - 1;
        return bill.get(newBill);
    }

    /**
     * Remove a bill in month bill
     *
     * @param billFile: bill file
     * @param index:    index of bill
     */
    public void removeBill(String billFile, int index) {
        List<BillModel> bills = getBillListFromFile(billFile);
        bills.remove(index);
        this.rewriteFile(bills, billFile);
    }

}
