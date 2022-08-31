package presentation.layer;

import business.logic.layer.SoftDinkBusiness;
import data.access.layer.SoftDrink;

import java.util.List;

public class SoftDrinkMenuTest {
    public static void main(String[] args) {
        SoftDinkBusiness softDinkBusiness = new SoftDinkBusiness();

        List<SoftDrink> softDinkBusinesses = softDinkBusiness.getSoftDinkBusinesses();
        softDinkBusiness.addItemToSoftDrink("aa", "bb", 1122);
        for (SoftDrink s: softDinkBusinesses) {
            System.out.println(s);
        }

    }
}
