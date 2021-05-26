package models;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String name;
    private String address;
    private Integer stars;
    private List<Customer> customers = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public Hotel(String name, String address, Integer stars) {
        this.name = name;
        this.address = address;
        this.stars = stars;
    }

    public void createRoom(Room room)
    {
        rooms.add(room);
    }

    public void createRoomsRange(List<Room> _rooms)
    {
        _rooms.forEach(r -> this.createRoom(r));
    }

    public void createEmployee(Employee employee)
    {
        if(!employees.stream().anyMatch(e -> e.getDni().equals(employee.getDni())))
        {
            employees.add(employee);
        }
    }

    public void createEmployeeRange(List<Employee> _employees)
    {
        _employees.forEach(e -> this.createEmployee(e));
    }

    public void createCustomer (Customer customer){
        if(!customers.stream().anyMatch(e -> e.getDni().equals(customer.getDni())))
        {
            customers.add(customer);
        }
    }

    public void createReceptionist(Employee employee){
        if(!employees.stream().anyMatch(e -> e.getDni().equals(employee.getDni())))
        {
            employees.add(employee);
        }
    }


}
