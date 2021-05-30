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

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public String getDni() {
        return this.dni;
    }

    public Boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", dni='" + dni + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
