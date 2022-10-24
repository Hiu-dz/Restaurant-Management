package repositories;

import models.BillModel;
import models.MenuModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class BillRepository {
    private static List<BillModel> billList;

    public BillRepository() {
        billList = new ArrayList<>();
    }

    /**
     * Get all date bill in directory
     *
     * @return list of date bill
     */
    public List<String> getAllDateBill() {
        File directoryPath = new File("BillData/");
        String[] contents = directoryPath.list();
        List<String> directories = new ArrayList<>();
        if (contents != null) {
            for (String content : contents) {
                File tempDirectory = new File("BillData/" + content);
                if (tempDirectory.isDirectory()) {
                    directories.add(content);
                }
            }
        }
        return directories.stream().sorted(Collections.reverseOrder()).toList();
    }

    /**
     * Get all bill in directory date bill
     *
     * @param dateBill: date of bill
     * @return list of date bill
     */
    public List<String> getAllBill(String dateBill) {
        File billPath = new File("BillData/" + dateBill);
        String[] contents = billPath.list();
        List<String> bills = new ArrayList<>();
        if (contents != null) {
            for (String content : contents) {
                File tempBill = new File("BillData/" + dateBill + "/" + content);
                if (tempBill.isFile()) {
                    bills.add(content);
                }
            }
        }
        return bills;
    }

    /**
     * Get a bill in directory date bill
     *
     * @param dateBill: date of bill
     * @param bill:     bill file
     * @return a bill
     */
    public List<String> getOneBill(String dateBill, String bill) {
        Path billPath = Paths.get("BillData/" + dateBill + "/" + bill);
        List<String> aBill = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(billPath)) {
            aBill = br.lines().toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aBill;
    }

    /**
     * Remove a bill in directory date bill
     *
     * @param dateBill: date of bill
     * @param bill:     bill file
     */
    public void removeBill(String dateBill, String bill) {
        Path billPath = Paths.get("BillData/" + dateBill + "/" + bill);
        try {
            Files.delete(billPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add menu item at bill list
     *
     * @param menuModel:   obj of menu model
     * @param quantity:    quantity of menu item
     * @param orderedTime: ordered time
     */
    public void addMenuItem(MenuModel menuModel, int quantity, LocalDate orderedTime) {
        Map<MenuModel, Integer> menuItems = new HashMap<>();
        menuItems.put(menuModel, quantity);
        billList.add(new BillModel(menuItems, orderedTime));
    }

    /**
     * Create bill name for bill file at directory date bill
     *
     * @param dateBill: date of bill
     * @return bill name
     */
    private String createBillName(String dateBill) {
        Path dateBillPath = Paths.get("BillData/" + dateBill);
        File dateBillDirectory = new File(dateBillPath.toUri());
        if (!dateBillDirectory.exists()) {
            try {
                Files.createDirectory(dateBillPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int billNumber = 1;
        String billName = "Bill" + billNumber + ".txt";
        List<String> bills = this.getAllBill(dateBill);
        for (String b : bills) {
            if (b.equals(billName)) {
                billNumber++;
                billName = "Bill" + billNumber + ".txt";
            }
        }
        return billName;
    }

    /**
     * Format date follow dd-MM-yyyy
     *
     * @param date: date
     * @return date has been formatted
     */
    private String formatDate(LocalDate date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(dateTimeFormatter);
    }

    /**
     * Create bill file at directory date bill
     *
     * @param dateBill: date of bill
     */
    public void createBill(String dateBill) {
        String billName = this.createBillName(dateBill);
        Path billPath = Paths.get("BillData/" + dateBill + "/" + billName);

        try (BufferedWriter bw = Files.newBufferedWriter(billPath)) {
            String billTitle = billName.replace(".txt", "");
            bw.write("+--------------------------- " + billTitle + " -------------------------+\n");
            bw.write("+---+------------+----------+---------------+---------------+\n");
            bw.write("| # | Item       | Quantity | Ordered Time  | Price Items   |\n");
            bw.write("+---+------------+----------+---------------+---------------+\n");
            int i = 1;
            double totalBill = 0;
            for (BillModel b : billList) {
                Map<MenuModel, Integer> menuItem = b.getMenuItem();
                String itemName = null;
                int itemQuantity = 0;
                double itemPrice = 0;
                for (Map.Entry<MenuModel, Integer> m : menuItem.entrySet()) {
                    itemName = m.getKey().getName();
                    itemQuantity = m.getValue();
                    itemPrice = m.getKey().getPrice();
                }
                String orderedTime = this.formatDate(b.getOrderedTime());
                double itemsPrice = itemQuantity * itemPrice;
                totalBill += itemsPrice;
                String s = String.format("| %s | %-11s| %-9s| %-14s| %-14s| %n", i++, itemName, itemQuantity, orderedTime, itemsPrice);
                bw.write(s);
            }
            bw.write("+--------------+------------+---------------+---------------+\n");
            String ss = String.format("| %s: %-45s| %n", "Total bill ", totalBill + " vnd");
            bw.write(ss);
            bw.write("+-----------------------------------------------------------+\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
