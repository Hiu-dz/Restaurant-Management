package views;

import controllers.BillController;

import java.util.List;
import java.util.Scanner;

public class BillView {
    private final BillController billController;

    public BillView() {
        this.billController = new BillController();
    }

    /**
     * Get a date bill
     *
     * @param dateBillNumber: number of date bill
     * @return date bill
     */
    private String getDateBill(int dateBillNumber) {
        return this.billController.getDateBill(dateBillNumber);
    }

    /**
     * Show bill management
     */
    public void showBillManagement() {
        List<String> directories = this.billController.getAllDateBill();
        int quantityDirectories = directories.size();
        System.out.println("------ BILL MANAGEMENT ------");
        System.out.println("**Date Bill**");
        if (quantityDirectories > 0) {
            for (int i = 0; i < quantityDirectories; i++) {
                int directoryNumber = i + 1;
                System.out.println(directoryNumber + ". " + directories.get(i));
            }
        } else {
            System.out.println("There is currently no date bill. Please come back later");
        }
        System.out.println("------");
        System.out.println("0. Exit program");
        System.out.println("b. Back system management");
        System.out.println("------");
        this.chooseDateBill();
    }

    /**
     * Choose date bill in show bill management
     */
    private void chooseDateBill() {
        MainView mainView = new MainView();
        Scanner scChoose = new Scanner(System.in);

        System.out.print("Enter the choose number: ");
        String choose = scChoose.nextLine();
        int number = this.billController.validateChooseDateBill(choose);
        int bills = 1;
        int back = 2;
        int warning = -1;
        if (number == bills) {
            this.showAllBillByDateBill(number);
        } else if (number == back) {
            mainView.showSystemManagement();
        } else if (number == warning) {
            this.chooseDateBill();
        }
    }

    /**
     * Show all bill when choose date bill number
     *
     * @param dateBillNumber: number of date bill
     */
    private void showAllBillByDateBill(int dateBillNumber) {
        List<String> bills = this.billController.getAllBill(dateBillNumber);
        int quantityBills = bills.size();
        String dateBill = this.getDateBill(dateBillNumber);
        System.out.println("---- " + dateBill + " - SHOW BILL ----");
        if (quantityBills > 0) {
            for (int i = 0; i < bills.size(); i++) {
                int billNumber = i + 1;
                System.out.println(billNumber + ". " + bills.get(i));
            }
        } else {
            System.out.println("No bill for this date. Please come back later");
        }
        System.out.println("------");
        this.chooseBill(dateBillNumber);
    }

    /**
     * Choose bill in date bill
     *
     * @param dateBillNumber: number of date bill
     */
    private void chooseBill(int dateBillNumber) {
        Scanner scChoose = new Scanner(System.in);

        System.out.print("Enter bill number: ");
        String choose = scChoose.nextLine();
        if (this.billController.isValidChooseBill(dateBillNumber, choose)) {
            this.displayBill(dateBillNumber, choose);
        } else {
            this.chooseBill(dateBillNumber);
        }
    }

    /**
     * Display a bill on console
     *
     * @param dateBillNumber: number of date bill
     * @param chooseNumber:   number of choose
     */
    private void displayBill(int dateBillNumber, String chooseNumber) {
        List<String> aBill = this.billController.getOneBill(dateBillNumber, chooseNumber);
        for (String b : aBill) {
            System.out.println(b);
        }
        System.out.println("------");
        this.chooseBillOptions(dateBillNumber, chooseNumber);
    }

    /**
     * Choose option when displayed bill
     *
     * @param dateBillNumber: number of date bill
     * @param chooseNumber:   number of choose
     */
    private void chooseBillOptions(int dateBillNumber, String chooseNumber) {
        Scanner scChoose = new Scanner(System.in);

        System.out.println("1. Remove this bill");
        System.out.println("2. Back date bill");
        System.out.println("0. Exit program");
        System.out.println("b. Back management");
        System.out.println("------");
        System.out.print("Enter your choose: ");
        String choose = scChoose.nextLine();

        int option = this.billController.validateChooseBillOption(choose);
        final int removeBill = 1;
        final int backDateBill = 2;
        final int backManagement = 3;

        switch (option) {
            case removeBill -> {
                this.billController.removeBill(dateBillNumber, chooseNumber);
                this.showAllBillByDateBill(dateBillNumber);
            }
            case backDateBill -> this.showAllBillByDateBill(dateBillNumber);
            case backManagement -> this.showBillManagement();
            default -> this.chooseBillOptions(dateBillNumber, chooseNumber);
        }
    }
}
