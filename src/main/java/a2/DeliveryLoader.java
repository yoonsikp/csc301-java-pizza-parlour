package a2;

import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class DeliveryLoader {
    public static ArrayList<String> getDeliveryMethods(String filename){
        ArrayList<String> deliveryMethods = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonTree = parser.parse(new FileReader(filename));
            JsonArray delivMeth = jsonTree.getAsJsonArray();

            for(int i = 0; i < delivMeth.size(); i++) {
                deliveryMethods.add(delivMeth.get(i).getAsString());
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return deliveryMethods;
    }
}
