package requests;

public class SetUserRequest {
    private String dni;
    private String name;
    private String country;
    private String address;

    public SetUserRequest(String dni, String name, String country, String address) {
        this.dni = dni;
        this.name = name;
        this.country = country;
        this.address = address;
    }

    public String getDni() {
        return dni;
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

}
