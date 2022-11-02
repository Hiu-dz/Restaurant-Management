package models;

public enum TypeMenu {
    SOFT_DRINK("Soft drink", "SoftDrink.json"),
    ALCOHOL("Alcohol", "Alcohol.json"),
    BREAKFAST("Breakfast", "Breakfast.json"),
    LUNCH("Lunch", "Lunch.json"),
    DINNER("Dinner", "Dinner.json");

    private final String name;
    private final String file;

    TypeMenu(String name, String file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public String getFile() {
        return file;
    }

    /**
     * Get menu file by type menu
     *
     * @param type: type menu
     * @return menu file
     */
    public static String getFileByType(TypeMenu type) {
        for (TypeMenu item : TypeMenu.values()) {
            if (item == type) {
                return item.getFile();
            }
        }
        return "";
    }

    /**
     * Get type menu from type number menu
     *
     * @param typeNumber: number of type
     * @return type menu
     */
    public static TypeMenu getTypeByNumber(int typeNumber) {
        int number = typeNumber - 1;
        if (number >= 0 && number < TypeMenu.values().length) {
            return TypeMenu.values()[number];
        } else {
            return null;
        }
    }

    /**
     * Get name of type menu
     *
     * @param typeNumber: number of type menu
     * @return name of type menu
     */
    public static String getNameByNumber(int typeNumber) {
        TypeMenu type = getTypeByNumber(typeNumber);
        if (type != null) {
            return type.getName();
        } else {
            return "";
        }
    }
}
