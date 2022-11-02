package services;

import models.BillModel;
import models.MenuModel;
import models.TypeMenu;
import repositories.BillRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BillService {
    private final BillRepository billRepository;

    public BillService() {
        this.billRepository = new BillRepository();
    }

    /**
     * Get all month bill from directory
     *
     * @return list of month bill
     */
    public List<String> getAllMonthBill() {
        return this.billRepository.getAllMonthBill();
    }

    /**
     * Get all bill from directory month bill
     *
     * @param monthBill: month of bill
     * @return list of bill
     */
    public List<BillModel> getAllBills(String monthBill) {
        if (monthBill != null) {
            String billFile = monthBill + ".json";
            return this.billRepository.getAllBill(billFile);
        } else {
            System.out.println("--> WARNING: Invalid month bill. Please try again");
            return new ArrayList<>();
        }
    }

    /**
     * Create a list will get all bills total price
     *
     * @param monthBill: month bill
     * @return list of all bills total price
     */
    public List<Double> getAllBillsTotalPrice(String monthBill) {
        if (monthBill != null) {
            String billFile = monthBill + ".json";
            List<Double> billsPrice = new ArrayList<>();
            List<BillModel> bills = this.billRepository.getAllBill(billFile);
            for (BillModel b : bills) {
                double totalPrice = 0;
                Map<MenuModel, Integer> menuItems = b.getMenuItem();
                for (Map.Entry<MenuModel, Integer> m : menuItems.entrySet()) {
                    MenuModel menuItem = m.getKey();
                    int quantity = m.getValue();
                    totalPrice += menuItem.getPrice() * quantity;
                }
                billsPrice.add(totalPrice);
            }
            return billsPrice;
        } else {
            System.out.println("--> WARNING: Invalid month bill. Please try again");
            return new ArrayList<>();
        }
    }

    /**
     * Get a bill in month bill
     *
     * @param monthBill:  month of bill
     * @param billNumber: bill file
     * @return a bill
     */
    public BillModel getOneBill(String monthBill, int billNumber) {
        String billFile = monthBill + ".json";
        int index = -1;
        for (int i = 0; i < this.billRepository.getAllBill(billFile).size(); i++) {
            if (billNumber == (i + 1)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            return this.billRepository.getOneBill(billFile, index);
        } else {
            System.out.println("--> WARNING: Invalid bill number. Please try again");
            return new BillModel();
        }
    }

    /**
     * Remove a bill in month bill
     *
     * @param monthBill:  month of bill
     * @param billNumber: bill number
     */
    public void removeBill(String monthBill, String billNumber) {
        String billFile = monthBill + ".json";
        int index = Integer.parseInt(billNumber) - 1;
        this.billRepository.removeBill(billFile, index);
    }

    /**
     * Add menu items at bill list
     *
     * @param menuItem: obj of menu model
     * @param quantity: quantity of menu item
     */
    public void addMenuItem(MenuModel menuItem, int quantity) {
        this.billRepository.addMenuItem(menuItem, quantity);
    }

    /**
     * Create bill file at month bill
     */
    public void createBill() {
        this.billRepository.createBill(this.billRepository.getMenuItemsList());
    }

    /**
     * Print bill when order done
     */
    public void printBillWhenOrderDone() {
        BillModel bill = this.billRepository.getBillWhenOrderDone();
        Map<MenuModel, Integer> menuItems = bill.getMenuItem();
        String orderedTime = bill.getOrderedTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        double totalPrice = 0;

        System.out.println("------ PAY THE BILL ------");
        for (Map.Entry<MenuModel, Integer> m : menuItems.entrySet()) {
            String name = m.getKey().getName();
            double price = m.getKey().getPrice();
            TypeMenu type = m.getKey().getType();
            int quantity = m.getValue();
            totalPrice += price * quantity;

            System.out.format("\t- Name: %-10s | Price: %-10s | Type: %-10s | Quantity: %s %n", name, price + " vnd", type, quantity);
        }
        System.out.println("** Ordered time: " + orderedTime);
        System.out.println("** Total price: " + totalPrice);
        System.out.println("------");

    }
}
