package enums;

public enum BedsEnum {
    DOUBLE_BED("Cama doble"),
    TWO_SINGLES("Dos simples"),
    DOUBLE_BED_AND_SINGLES("Doble y simples"),
    FOUR_SINGLES("Cuatro simples");

    private final String name;

    private BedsEnum(String name) {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
