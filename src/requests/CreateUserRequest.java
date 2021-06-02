package requests;

public class CreateUserRequest {
    private String dni;
    private String name;
    private String country;
    private String address;
    private String password;

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

    public String getPassword() {
        return password;
    }
}
