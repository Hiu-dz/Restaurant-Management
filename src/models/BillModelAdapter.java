package models;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class BillModelAdapter implements JsonSerializer<BillModel>, JsonDeserializer<BillModel> {
    /**
     * Custom write json file of bill
     *
     * @param billModel: object of BillModel class
     * @return new format json file of bill
     */
    @Override
    public JsonElement serialize(BillModel billModel, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject billObject = new JsonObject();
        JsonArray menuItemArray = new JsonArray();

        String valueOrderedTime = billModel.getOrderedTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        for (Map.Entry<MenuModel, Integer> menuItem : billModel.getMenuItem().entrySet()) {
            JsonObject menuItemObject = new JsonObject();

            String valueName = menuItem.getKey().getName();
            String valueDescription = menuItem.getKey().getDescription();
            String valuePrice = String.valueOf(menuItem.getKey().getPrice());
            String valueType = String.valueOf(menuItem.getKey().getType());
            String valueQuantity = String.valueOf(menuItem.getValue());

            menuItemObject.addProperty("name", valueName);
            menuItemObject.addProperty("description", valueDescription);
            menuItemObject.addProperty("price", valuePrice);
            menuItemObject.addProperty("type", valueType);
            menuItemObject.addProperty("quantity", valueQuantity);

            menuItemArray.add(menuItemObject);
        }
        billObject.add("menuItems", menuItemArray);
        billObject.addProperty("orderedTime", valueOrderedTime);

        return billObject;
    }

    /**
     * Custom read json file of bill
     *
     * @return BillModel
     */
    @Override
    public BillModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject billObject = jsonElement.getAsJsonObject();

        JsonArray getMenuItems = billObject.get("menuItems").getAsJsonArray();
        String getOrderedTime = billObject.get("orderedTime").getAsString();
        Map<MenuModel, Integer> menuItems = new HashMap<>();
        LocalDate orderedTime = LocalDate.parse(getOrderedTime, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        for (int i = 0; i < getMenuItems.size(); i++) {
            JsonElement element = getMenuItems.get(i);

            String name = element.getAsJsonObject().get("name").getAsString();
            String description = element.getAsJsonObject().get("description").getAsString();
            double price = Double.parseDouble(element.getAsJsonObject().get("price").getAsString());
            TypeMenu typeMenu = TypeMenu.valueOf(element.getAsJsonObject().get("type").getAsString());
            Integer quantity = Integer.parseInt(element.getAsJsonObject().get("quantity").getAsString());

            MenuModel menuModel = new MenuModel(name, description, price, typeMenu);
            menuItems.put(menuModel, quantity);
        }

        return new BillModel(menuItems, orderedTime);
    }
}
