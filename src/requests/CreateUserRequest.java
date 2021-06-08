package requests;

import enums.RoleEnum;

public class CreateUserRequest {
    private String dni;
    private String name;
    private String country;
    private String address;
    private String password;
    private RoleEnum role;

    public CreateUserRequest(String name, String dni, String country, String address, String password, RoleEnum role) {
        this.dni = dni;
        this.name = name;
        this.country = country;
        this.address = address;
        this.password = password;
        this.role = role;
    }

    public RoleEnum getRole() {
        return role;
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

    public String getPassword() {
        return password;
    }
}
