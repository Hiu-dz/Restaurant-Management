package controllers;

import models.BillModel;
import services.BillService;

import java.util.List;

public class BillController {
    private final BillService billService;

    public BillController() {
        this.billService = new BillService();
    }

    /**
     * Get all month bill in directory
     *
     * @return list of month bill
     */
    public List<String> getAllMonthBill() {
        return this.billService.getAllMonthBill();
    }

    /**
     * Validate choose number of choose month bill
     *
     * @param choose: string choose
     * @return true if valid choose month bill
     */
    public int validateChooseMonthBill(String choose) {
        try {
            int number = Integer.parseInt(choose);
            if (number >= 0) {
                if (number == 0) {
                    System.out.println("--> ALERT: Program will exit. See you again");
                    System.exit(0);
                    return 0;
                }
                return 1;
            } else {
                System.out.println("--> WARNING: Can only enter positive integer. Please try again");
                return -1;
            }
        } catch (NumberFormatException e) {
            if (choose.equals("b")) {
                return 2;
            }
            System.out.println("--> WARNING: Can only enter positive integer. Please try again");
            return -1;
        }
    }

    /**
     * Get a month bill in list of month bill
     *
     * @param monthBillNumber: number of month bill in list
     * @return month bill
     */
    public String getMonthBill(int monthBillNumber) {
        List<String> monthBills = this.billService.getAllMonthBill();
        int quantityMonthBills = monthBills.size();
        String monthBill = null;
        for (int i = 0; i < quantityMonthBills; i++) {
            int locationMonthBill = i + 1;
            if (monthBillNumber == locationMonthBill) {
                monthBill = monthBills.get(i);
                break;
            }
        }

        return monthBill;
    }

    /**
     * Get all bill in bill list
     *
     * @param monthBillNumber: number of data bill in list
     * @return bill list
     */
    public List<BillModel> getAllBill(int monthBillNumber) {
        String monthBill = this.getMonthBill(monthBillNumber);
        return this.billService.getAllBills(monthBill);
    }

    /**
     * Validate choose number for bill
     *
     * @param monthBillNumber: number of month bill
     * @param chooseNumber:    choose number
     * @return true if valid choose bill
     */
    public int validChooseBill(int monthBillNumber, String chooseNumber) {
        String monthBill = this.getMonthBill(monthBillNumber);
        int quantityBill = this.billService.getAllBills(monthBill).size();
        try {
            int number = Integer.parseInt(chooseNumber);
            if (number > 0 && number <= quantityBill) {
                return 1;
            } else if (number == 0) {
                return 0;
            } else {
                System.out.println("--> WARNING: Can not enter more than quantity bill. Please try again");
                return -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("--> WARNING: Can only enter positive integer. Please try again");
            return -1;
        }
    }

    public List<Double> getAllBillsTotalPrice(int monthBillNumber) {
        String monthBill = this.getMonthBill(monthBillNumber);
        return this.billService.getAllBillsTotalPrice(monthBill);
    }

    /**
     * Get a bill
     *
     * @param monthBillNumber: number of month bill
     * @param billNumber:      number of bill
     * @return a bill
     */
    public BillModel getOneBill(int monthBillNumber, String billNumber) {
        String monthBill = this.getMonthBill(monthBillNumber);
        return this.billService.getOneBill(monthBill, Integer.parseInt(billNumber));
    }

    /**
     * Validate choose bill option
     *
     * @param choose: choose of option
     * @return 1 if remove bill, 0 if exit program and b if back bill management
     */
    public int validateChooseBillOption(String choose) {
        final String removeBill = "1";
        final String exitProgram = "0";
        final String backManagement = "b";

        switch (choose) {
            case removeBill -> {
                return 1;
            }
            case exitProgram -> {
                System.out.println("--> ALERT: Program will exit. See you again");
                System.exit(0);
                return 0;
            }
            case backManagement -> {
                return 3;
            }
            default -> {
                System.out.println("--> WARNING: Invalid input. Please try again");
                return -1;
            }
        }
    }

    /**
     * Remove bill file in month bill
     *
     * @param monthBillNumber: number of month bill
     * @param billNumber:      number of bill
     */
    public void removeBill(int monthBillNumber, String billNumber) {
        String monthBill = this.getMonthBill(monthBillNumber);
        this.billService.removeBill(monthBill, billNumber);
    }
}
