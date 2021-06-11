package enums;

public enum RoleEnum {
    ADMIN ("Admin"),
    RECEPTIONIST("Recepcionista"),
    USER("Usuario");

    private final String name;

    private RoleEnum(String name) {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
