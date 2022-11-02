package views;

import controllers.MainController;

import java.util.Scanner;

public class MainView {
    private final MainController mainController;

    public MainView() {
        this.mainController = new MainController();
    }

    /**
     * Show system of restaurant management
     */
    public void showSystem() {
        System.out.println("##### Welcome to Hiu 2nd Restaurant #####");
        System.out.println("1. Order menu item");
        System.out.println("2. System management");
        System.out.println("------");
        System.out.println("0. Exit program");
        this.chooseFunction();
    }

    /**
     * Choose function of system restaurant management
     */
    private void chooseFunction() {
        MenuView menuView = new MenuView();
        Scanner scChoose = new Scanner(System.in);
        System.out.println("------");
        System.out.print("Enter your choose: ");
        String chooseNumber = scChoose.nextLine();
        int number = this.mainController.validateChooseSystem(chooseNumber);
        int orderMenuItem = 1;
        int systemManagement = 2;
        int warning = -1;
        if (number == orderMenuItem) {
            menuView.showMenu();
        } else if (number == systemManagement) {
            this.showSystemManagement();
        } else if (number == warning) {
            this.chooseFunction();
        }
    }

    /**
     * Show system management
     */
    public void showSystemManagement() {
        System.out.println("------ SYSTEM MANAGEMENT ------");
        System.out.println("1. Menu management");
        System.out.println("2. Bill management");
        System.out.println("------");
        System.out.println("0. Exit program");
        System.out.println("b. Back system");
        this.chooseSystemManagement();
    }

    /**
     * Choose system management
     */
    private void chooseSystemManagement() {
        MenuView menuView = new MenuView();
        BillView billView = new BillView();
        Scanner scChoose = new Scanner(System.in);
        System.out.println("------");
        System.out.print("Enter your choose: ");
        String choose = scChoose.nextLine();
        int number = this.mainController.validateChooseSystemManagement(choose);
        int menuManagement = 1;
        int billManagement = 2;
        int backSystem = 3;
        if (number == menuManagement) {
            menuView.showManagement();
        } else if (number == billManagement) {
            billView.showBillManagement();
        } else if (number == backSystem) {
            this.showSystem();
        }
    }
}
