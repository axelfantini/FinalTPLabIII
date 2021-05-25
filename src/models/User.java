package models;

public abstract class User {
    protected String name;
    protected String dni;
    protected String country;
    protected String address;
    protected String password;

    public User(String name, String dni, String country, String address, String password) {
        this.name = name;
        this.dni = dni;
        this.country = country;
        this.address = address;
        this.password = password;
    }

    public String getDni() {
        return this.dni;
    }
}
