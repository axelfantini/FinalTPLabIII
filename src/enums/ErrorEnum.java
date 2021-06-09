package enums;

public enum ErrorEnum {
    USER_WITH_SAME_DNI ("Ya existe un usuario con ese DNI"),
    ROOM_WITH_SAME_NUM ("Ya existe una habitacion con ese numero"),
    USER_NOT_FOUND ("No existe ese usuario"),
    VIEW_NOT_FOUND ("Vista no encontrada"),
    ROOMTYPE_NOT_FOUND ("No existe ese tipo de habitacion"),
    ROOM_NOT_FOUND ("No existe la habitación");

    private final String fancyError;

    private ErrorEnum(String fancyError) {
        this.fancyError = fancyError;
    }

    public String getFancyError()
    {
        return this.fancyError;
    }
}
