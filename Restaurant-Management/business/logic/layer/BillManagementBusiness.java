package business.logic.layer;

import data.access.layer.BillManagement;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BillManagementBusiness {
    private static final List<BillManagement> billManagements = billManagements();

    public List<BillManagement> getBillManagements() {
        return billManagements;
    }

    public static List<BillManagement> billManagements() {
        return new ArrayList<>();
    }

    private boolean hasDateExportBill(String dateBill) {
        return Files.exists(Paths.get("data\\bill\\export\\" + dateBill));
    }

    private String getCurrentDate() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return myDateObj.format(myFormatObj);
    }

    private void createDirectory(String directoryName) {
        try {
            Path path = Paths.get("data\\bill\\export\\" + directoryName);
            Files.createDirectory(path);
        } catch (IOException io) {
            System.out.println("Have error");
        }
    }

    private double totalBillPrice(List<BillManagement> listName) {
        double totalPrice = 0;
        for (BillManagement b : listName) {
            totalPrice += b.getPriceItems();
        }

        return totalPrice;
    }

    private boolean hasBill(String billName) {
        return Files.exists(Paths.get("data\\bill\\export\\" + getCurrentDate() + "\\" + billName));
    }

    public void addItem(String menuItem, int quantity, double unitPrice) {
        double priceItems = quantity * unitPrice;
        this.getBillManagements().add(new BillManagement(menuItem, quantity, priceItems));
    }

    public void showBill() {
        System.out.format("+----------------------------- Bill ------------------------------+%n");
        System.out.format("+---+------------+----------+---------------------+---------------+%n");
        System.out.format("| # | Item       | Quantity | Ordered Time        | Price Items   |%n");
        System.out.format("+---+------------+----------+---------------------+---------------+%n");
        int i = 1;
        for (BillManagement b : this.getBillManagements()) {
            System.out.format("| %s | %-11s| %-9s| %-20s| %-14s| %n", i++, b.getMenuItem(), b.getQuantity(), b.getOrderedTime(), b.getPriceItems());
        }
        System.out.format("+----------------+-----------+---------------------+--------------+%n");
        System.out.format("| Enter 'export' | Export to export bill                          |%n");
        System.out.format("| Enter 'exit'   | Exit program                                   |%n");
        System.out.format("+----------------+------------------------------------------------+%n");

    }

    public void exportBill() {
        if (!hasDateExportBill(getCurrentDate())) {
            createDirectory(getCurrentDate());
        }

        int fileNum = 1;
        int nameNum = 1;
        String billFile = "Bill" + fileNum + ".txt";
        String billName = "Bill " + nameNum;
        while (hasBill(billFile)) {
            billFile = "Bill" + fileNum++ + ".txt";
            billName = "Bill " + nameNum++;
        }

        String url = "data/bill/export/" + getCurrentDate() + "/" + billFile;
        Path path = Paths.get(url);
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.write("+---------------------------- " + billName + " -----------------------------+\n");
            bw.write("+---+------------+----------+---------------------+---------------+\n");
            bw.write("| # | Item       | Quantity | Ordered Time        | Price Items   |\n");
            bw.write("+---+------------+----------+---------------------+---------------+\n");
            int i = 1;
            for (BillManagement b : this.getBillManagements()) {
                String s = String.format("| %s | %-11s| %-9s| %-20s| %-14s| %n", i++, b.getMenuItem(), b.getQuantity(), b.getOrderedTime(), b.getPriceItems());
                bw.write(s);
            }
            bw.write("+---------------+-----------+---------------------+---------------+\n");
            double totalPrice = totalBillPrice(this.getBillManagements());
            String ss = String.format("| %s: %-51s| %n", "Total price", totalPrice);
            bw.write(ss);
            bw.write("+-----------------------------------------------------------------+\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}