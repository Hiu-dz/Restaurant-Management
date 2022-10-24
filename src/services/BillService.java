package services;

import models.MenuModel;
import repositories.BillRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class BillService {
    private final BillRepository billRepository;

    public BillService() {
        this.billRepository = new BillRepository();
    }

    /**
     * Get all date bill from directory
     *
     * @return list of date bill
     */
    public List<String> getAllDateBill() {
        return this.billRepository.getAllDateBill();
    }

    /**
     * Get all bill from directory date bill
     *
     * @param dateBill: date of bill
     * @return list of bill
     */
    public List<String> getAllBill(String dateBill) {
        if (dateBill != null) {
            return this.billRepository.getAllBill(dateBill);
        } else {
            System.out.println("--> WARNING: Invalid date bill. Please try again");
            return Collections.emptyList();
        }
    }

    /**
     * Get a bill in directory date bill
     *
     * @param dateBill: date of bill
     * @param bill:     bill file
     * @return a bill
     */
    public List<String> getOneBill(String dateBill, String bill) {
        return this.billRepository.getOneBill(dateBill, bill);
    }

    /**
     * Remove a bill in directory date bill
     *
     * @param dateBill: date of bill
     * @param bill:     bill file
     */
    public void removeBill(String dateBill, String bill) {
        this.billRepository.removeBill(dateBill, bill);
    }

    /**
     * Add menu items at bill list
     *
     * @param menuItem:    obj of menu model
     * @param quantity:    quantity of menu item
     * @param orderedTime: ordered time
     */
    public void addMenuItem(MenuModel menuItem, int quantity, LocalDate orderedTime) {
        this.billRepository.addMenuItem(menuItem, quantity, orderedTime);
    }

    /**
     * Create bill file at directory date bill
     *
     * @param dateBill: date of bill
     */
    public void createBill(String dateBill) {
        this.billRepository.createBill(dateBill);
    }
}
