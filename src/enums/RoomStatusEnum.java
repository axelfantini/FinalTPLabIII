package enums;

public enum RoomStatusEnum {
    NOT_AVAILABLE ("No disponible"),
    OCCUPIED ("Ocupada"),
    UNOCCUPIED ("Desocupada");

    private final String name;

    private RoomStatusEnum(String fancyError) {
        this.name = fancyError;
    }

    public String getName()
    {
        return this.name;
    }

}
