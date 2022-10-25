package views;

import controllers.MainController;

import java.util.Scanner;

public class MainView {
    private final MainController mainController;
    private final MenuView menuView;
    private final BillView billView;

    public MainView() {
        this.mainController = new MainController();
        this.menuView = new MenuView();
        this.billView = new BillView();
    }

    public void showSystem() {
        System.out.println("##### Welcome to Hiu 2nd Restaurant #####");
        System.out.println("1. Order menu item");
        System.out.println("2. System management");
        System.out.println("------");
        System.out.println("0. Exit program");
        this.chooseSystem();
    }

    private void chooseSystem() {
        Scanner scChoose = new Scanner(System.in);
        System.out.println("------");
        System.out.print("Enter your choose: ");
        String chooseNumber = scChoose.nextLine();
        int number = this.mainController.validateChooseSystem(chooseNumber);
        int orderMenuItem = 1;
        int systemManagement = 2;
        int warning = -1;
        if (number == orderMenuItem) {
            this.menuView.showMenu();
        } else if (number == systemManagement) {
            this.showSystemManagement();
        } else if (number == warning) {
            this.chooseSystem();
        }
    }

    public void showSystemManagement() {
        System.out.println("------ SYSTEM MANAGEMENT ------");
        System.out.println("1. Menu management");
        System.out.println("2. Bill management");
        System.out.println("------");
        System.out.println("0. Exit program");
        System.out.println("b. Back system");
        this.chooseSystemManagement();
    }

    private void chooseSystemManagement() {
        Scanner scChoose = new Scanner(System.in);
        System.out.println("------");
        System.out.print("Enter your choose: ");
        String choose = scChoose.nextLine();
        int number = this.mainController.validateChooseSystemManagement(choose);
        int menuManagement = 1;
        int billManagement = 2;
        int backSystem = 3;
        if (number == menuManagement) {
            this.menuView.showManagement();
        } else if (number == billManagement) {
            this.billView.showBillManagement();
        } else if (number == backSystem) {
            this.showSystem();
        }
    }
}
