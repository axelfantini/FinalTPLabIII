package models;

import java.util.ArrayList;
import java.util.List;

public class ViewParams {
    private List<ViewParamsItem> items = new ArrayList<>();

    public void setItem(String key, String data)
    {
        ViewParamsItem item = items.stream().filter(i -> i.getKey().equals(key)).findFirst().orElse(null);
        if(item != null)
            item.setData(data);
        else
            items.add(new ViewParamsItem(key, data));
    }

    public void removeItem(String key)
    {
        ViewParamsItem item = items.stream().filter(i -> i.getKey().equals(key)).findFirst().orElse(null);
        if(item != null)
            items.remove(item);
    }

    public String getValue(String key)
    {
        String response = "";
        ViewParamsItem item = items.stream().filter(i -> i.getKey().equals(key)).findFirst().orElse(null);
        if(item != null)
            response = item.getData();
        return response;
    }
}
