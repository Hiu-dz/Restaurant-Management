package views;

import controllers.BillController;
import models.BillModel;
import models.MenuModel;
import models.TypeMenu;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BillView {
    private final BillController billController;

    public BillView() {
        this.billController = new BillController();
    }

    /**
     * Show bill management
     */
    public void showBillManagement() {
        List<String> directories = this.billController.getAllMonthBill();
        int quantityDirectories = directories.size();
        System.out.println("------ BILL MANAGEMENT ------");
        System.out.println("**Month Bill**");
        if (quantityDirectories > 0) {
            for (int i = 0; i < quantityDirectories; i++) {
                int directoryNumber = i + 1;
                System.out.println(directoryNumber + ". " + directories.get(i));
            }
        } else {
            System.out.println("There is currently no month bill. Please come back later");
        }
        System.out.println("------");
        System.out.println("0. Exit program");
        System.out.println("b. Back system management");
        System.out.println("------");
        this.chooseMonthBill();
    }

    /**
     * Choose month bill in show bill management
     */
    private void chooseMonthBill() {
        MainView mainView = new MainView();
        Scanner scChoose = new Scanner(System.in);

        System.out.print("Enter the choose number: ");
        String choose = scChoose.nextLine();
        int number = this.billController.validateChooseMonthBill(choose);
        int bills = 1;
        int back = 2;
        int warning = -1;
        if (number == bills) {
            this.showAllBillByMonthBill(Integer.parseInt(choose));
        } else if (number == back) {
            mainView.showSystemManagement();
        } else if (number == warning) {
            this.chooseMonthBill();
        }
    }

    /**
     * Show all bill when choose month bill number
     *
     * @param monthBillNumber: number of month bill
     */
    private void showAllBillByMonthBill(int monthBillNumber) {
        List<BillModel> bills = this.billController.getAllBill(monthBillNumber);
        List<Double> billsTotalPrice = this.billController.getAllBillsTotalPrice(monthBillNumber);

        String monthBill = this.billController.getMonthBill(monthBillNumber);
        double monthTotalPrice = 0;
        System.out.println("------ " + monthBill + " - SHOW ALL BILL ------");
        if (!bills.isEmpty()) {
            for (int i = 0; i < bills.size(); i++) {
                String billName = "Bill " + (i + 1);
                Map<MenuModel, Integer> menuItems = bills.get(i).getMenuItem();
                String orderedTime = bills.get(i).getOrderedTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                double totalPrice = billsTotalPrice.get(i);
                monthTotalPrice += totalPrice;

                System.out.println("### " + billName);
                System.out.println("** Menu items:");
                for (Map.Entry<MenuModel, Integer> m : menuItems.entrySet()) {
                    String name = m.getKey().getName();
                    double price = m.getKey().getPrice();
                    int quantity = m.getValue();
                    TypeMenu type = m.getKey().getType();

                    System.out.format("\t- Name: %-10s | Price: %-10s | Type: %-10s | Quantity: %s %n", name, price + " vnd", type, quantity);
                }
                System.out.println("** Ordered time: " + orderedTime);
                System.out.println("** Total price: " + totalPrice + "vnd");
                System.out.println("------");
            }
        } else {
            System.out.println("This month's bill is no longer billed. Please comeback later");
        }
        System.out.printf("*** Total price of %s: %.1f vnd %n", monthBill, monthTotalPrice);
        System.out.println("------");
        this.chooseBill(monthBillNumber);
    }

    /**
     * Choose bill in month bill
     *
     * @param monthBillNumber: number of month bill
     */
    private void chooseBill(int monthBillNumber) {
        Scanner scChoose = new Scanner(System.in);

        System.out.print("Enter bill number or '0' to back: ");
        String choose = scChoose.nextLine();
        int status = this.billController.validChooseBill(monthBillNumber, choose);
        int back = 0;
        int warning = -1;
        if (status == back) {
            this.showBillManagement();
        } else if (status == warning) {
            this.chooseBill(monthBillNumber);
        } else {
            this.chooseBillOptions(monthBillNumber, choose);
        }
    }

    /**
     * Display a bill on console
     *
     * @param monthBillNumber: number of month bill
     * @param chooseNumber:    number of choose
     */
    private void displayBill(int monthBillNumber, String chooseNumber) {
        BillModel bill = this.billController.getOneBill(monthBillNumber, chooseNumber);
        Map<MenuModel, Integer> menuItems = bill.getMenuItem();
        String orderedTime = bill.getOrderedTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        double totalPrice = 0;

        System.out.println("------ BILL ------");
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

    /**
     * Choose option when displayed bill
     *
     * @param monthBillNumber: number of month bill
     * @param chooseNumber:    number of choose
     */
    private void chooseBillOptions(int monthBillNumber, String chooseNumber) {
        Scanner scChoose = new Scanner(System.in);

        this.displayBill(monthBillNumber, chooseNumber);

        System.out.println("1. Remove this bill");
        System.out.println("0. Exit program");
        System.out.println("b. Back management");
        System.out.println("------");
        System.out.print("Enter your choose: ");
        String choose = scChoose.nextLine();

        int option = this.billController.validateChooseBillOption(choose);
        final int removeBill = 1;
        final int backManagement = 3;

        switch (option) {
            case removeBill -> {
                this.billController.removeBill(monthBillNumber, chooseNumber);
                this.showBillManagement();
            }
            case backManagement -> this.showBillManagement();
            default -> this.chooseBillOptions(monthBillNumber, chooseNumber);
        }
    }
}
