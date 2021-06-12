package models;

import requests.SetRoomTypeRequest;

import java.util.UUID;

public class RoomType extends BaseObject<UUID> {
    private String name;
    private int capacity;
    private Double price;

    public RoomType(String name, int capacity, Double price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.capacity = capacity;
        this.price = price;
    }


    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setValues(SetRoomTypeRequest values)
    {
        this.name = values.getName();
        this.capacity = values.getCapacity();
        this.price = values.getPrice();
    }
}
