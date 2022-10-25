package controllers;

import services.BillService;

import java.util.List;

public class BillController {
    private final BillService billService;

    public BillController() {
        this.billService = new BillService();
    }

    /**
     * Get all date bill in directory
     *
     * @return list of date bill
     */
    public List<String> getAllDateBill() {
        return this.billService.getAllDateBill();
    }

    /**
     * Validate choose number of choose date bill
     *
     * @param choose: string choose
     * @return true if valid choose date bill
     */
    public int validateChooseDateBill(String choose) {
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
     * Get a date bill in list of date bill
     *
     * @param dateBillNumber: number of date bill in list
     * @return date bill
     */
    public String getDateBill(int dateBillNumber) {
        List<String> dateBills = this.billService.getAllDateBill();
        int quantityDateBills = dateBills.size();
        String dateBill = null;
        for (int i = 0; i < quantityDateBills; i++) {
            int locationDateBill = i + 1;
            if (dateBillNumber == locationDateBill) {
                dateBill = dateBills.get(i);
                break;
            }
        }
        return dateBill;
    }

    /**
     * Get all bill in bill list
     *
     * @param dateBillNumber: number of data bill in list
     * @return bill list
     */
    public List<String> getAllBill(int dateBillNumber) {
        String dateBill = this.getDateBill(dateBillNumber);
        return this.billService.getAllBill(dateBill);
    }

    /**
     * Validate choose number for bill
     *
     * @param dateBillNumber: number of date bill
     * @param chooseNumber:   choose number
     * @return true if valid choose bill
     */
    public boolean isValidChooseBill(int dateBillNumber, String chooseNumber) {
        String dateBill = this.getDateBill(dateBillNumber);
        int quantityBill = this.billService.getAllBill(dateBill).size();
        try {
            int number = Integer.parseInt(chooseNumber);
            if (number > 0 && number <= quantityBill) {
                return true;
            } else {
                System.out.println("--> WARNING: Can not enter more than quantity bill. Please try again");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("--> WARNING: Can only enter positive integer. Please try again");
            return false;
        }
    }

    /**
     * Get a bill file in bill list
     *
     * @param dateBill:   date of bill
     * @param billNumber: number of bill
     * @return bill file
     */
    private String getBillFile(String dateBill, String billNumber) {
        List<String> bills = this.billService.getAllBill(dateBill);
        String bill = null;
        int number = Integer.parseInt(billNumber);
        for (int i = 0; i < bills.size(); i++) {
            int ordinalNumber = i + 1;
            if (number == ordinalNumber) {
                bill = bills.get(i);
                break;
            }
        }
        return bill;
    }

    /**
     * Get a bill
     *
     * @param dateBillNumber: number of date bill
     * @param billNumber:     number of bill
     * @return a bill
     */
    public List<String> getOneBill(int dateBillNumber, String billNumber) {
        String dateBill = this.getDateBill(dateBillNumber);
        String bill = this.getBillFile(dateBill, billNumber);

        return this.billService.getOneBill(dateBill, bill);
    }

    /**
     * Validate choose bill option
     *
     * @param choose: choose of option
     * @return 1 if remove bill, 2 if back date bill, 0 if exit program and b if back bill management
     */
    public int validateChooseBillOption(String choose) {
        final String removeBill = "1";
        final String backDateBill = "2";
        final String exitProgram = "0";
        final String backManagement = "b";

        switch (choose) {
            case removeBill -> {
                return 1;
            }
            case backDateBill -> {
                return 2;
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
     * Remove bill file in directory
     *
     * @param dateBillNumber: number of date bill
     * @param billNumber:     number of bill
     */
    public void removeBill(int dateBillNumber, String billNumber) {
        String dateBill = this.getDateBill(dateBillNumber);
        String bill = this.getBillFile(dateBill, billNumber);

        this.billService.removeBill(dateBill, bill);
    }
}
