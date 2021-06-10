package helpers;

import com.google.gson.Gson;
import models.ErrorResponse;
import models.Hotel;
import models.Room;

import java.io.*;
import java.util.List;

public class SaveInfoHelper {
    public static void /*ErrorResponse<String>*/ saveHotel(Hotel hotel, File file){
        Gson gson = new Gson();

        String json = gson.toJson(hotel);

        try {
            FileWriter writer = new FileWriter(file.getAbsolutePath());
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(json);
    }

    public static void readHotel(File file){
        Gson gson = new Gson();

        try {

            System.out.println("Reading JSON from a file");
            System.out.println("----------------------------");

            BufferedReader br = new BufferedReader(
                    new FileReader(file.getAbsolutePath()));

            //convert the json string back to object
            Hotel hotel = gson.fromJson(br, Hotel.class);

            System.out.println("Hotel: "+hotel.getName());


            System.out.println("Population: "+hotel.getStars());

            List<Room> rooms = hotel.getRooms();

            System.out.println(rooms.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}