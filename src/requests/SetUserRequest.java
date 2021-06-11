package requests;

import enums.RoleEnum;

public class SetUserRequest {
    private String dni;
    private String name;
    private String country;
    private String address;
    private String password;
    private RoleEnum role;

    public SetUserRequest(String dni, String name, String country, String address, String password, RoleEnum role) {
        this.dni = dni;
        this.name = name;
        this.country = country;
        this.address = address;
        this.role = role;
        this.password = password;
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

    public RoleEnum getRole() {
        return role;
    }
}
