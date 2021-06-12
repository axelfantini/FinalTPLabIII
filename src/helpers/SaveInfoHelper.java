package helpers;

import com.google.gson.Gson;
import enums.ErrorEnum;
import models.ErrorResponse;

import java.io.*;

public class SaveInfoHelper {
    public static <T> ErrorResponse<T> saveFile(T item, String path){
        Gson gson = new Gson();
        createDirectory(path);
        String json = gson.toJson(item);
        ErrorResponse<T> errorResponse = new ErrorResponse<>();
        try {

            FileWriter writer = new FileWriter(path);
            writer.write(json);
            writer.close();
            errorResponse.setSuccess(true);
            errorResponse.setBody(item);
        } catch (IOException e) {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.SAVE_FILE_ERROR);
        }

        return errorResponse;
    }

    private static void createDirectory(String path)
    {
        String newPath = "";
        String[] splitPath = path.split("\\\\");
        for(int i = 0; i < splitPath.length - 1; i++)
            newPath += splitPath[i] + "\\";

        File theDir = new File(newPath);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
    }

    public static <T> ErrorResponse<T> readFile(String path, Class<T> tClass){
        Gson gson = new Gson();
        ErrorResponse<T> errorResponse = new ErrorResponse<>();
        try {

            BufferedReader br = new BufferedReader(
                    new FileReader(path));

            T item = gson.fromJson(br, tClass);
            errorResponse.setSuccess(true);
            errorResponse.setBody(item);
            br.close();

        } catch (IOException e) {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.OPEN_FILE_ERROR);
        }
        return errorResponse;
    }
}