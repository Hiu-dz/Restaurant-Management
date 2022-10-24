package controllers;

public class MainController {
    public int validateChooseSystem(String chooseNumber) {
        try {
            int number = Integer.parseInt(chooseNumber);
            final int orderMenuItem = 1;
            final int systemManagement = 2;
            final int exitProgram = 0;
            switch (number) {
                case orderMenuItem -> {
                    return 1;
                }
                case systemManagement -> {
                    return 2;
                }
                case exitProgram -> {
                    System.out.println("--> ALERT: Program will exit. Please try again");
                    System.exit(0);
                    return 0;
                }
                default -> {
                    System.out.println("--> WARNING: Can only enter '0' to '2'. Please try again");
                    return -1;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("-->WARNING: Can only enter positive number. Please try again");
            return -1;
        }
    }
}
