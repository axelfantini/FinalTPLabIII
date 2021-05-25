package models;

public class Employee extends User {
    private Boolean isAdmin;

    public Employee(String name, String dni, String country, String address, String password,Boolean isAdmin) {
        super(name,dni,country,address,password);
        this.isAdmin = isAdmin;
    }



    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }


}
