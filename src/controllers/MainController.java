package controllers;

public class MainController {
    /**
     * Validate choose system
     *
     * @param chooseNumber: choose number
     * @return 1 if is order menu item, 2 if is system management and 0 if is exit program
     */
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

    /**
     * Validate choose system management
     *
     * @param choose: string choose
     * @return 1 if is menu management, 2 if is bill management, 0 if is exit program
     */
    public int validateChooseSystemManagement(String choose) {
        try {
            int number = Integer.parseInt(choose);
            final int menuManagement = 1;
            final int billManagement = 2;
            final int exitProgram = 0;
            switch (number) {
                case menuManagement -> {
                    return 1;
                }
                case billManagement -> {
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
            String backSystem = "b";
            if (choose.equals(backSystem)) {
                return 3;
            }
            System.out.println("-->WARNING: Can only enter positive number or 'bb'. Please try again");
            return -1;
        }
    }
}
