package a2;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class MenuLoader {
    public static Menu getPopulatedMenu(String filename){
        Menu myMenu = new Menu();
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonTree = parser.parse(new FileReader(filename));
            if(jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();

                // Populate pizzas
                JsonObject pizzas = jsonObject.getAsJsonObject("pizzas");
                Set<Map.Entry<String, JsonElement>> pizzaEntries = pizzas.entrySet();
                HashMap<String, HashMap<String, Float>> finalPizzaEntries = new HashMap<String, HashMap<String, Float>>();
                for(Map.Entry<String, JsonElement> entry: pizzaEntries){
                    HashMap<String, Float> newSizeEntries = new HashMap<String, Float>();
                    Set<Map.Entry<String, JsonElement>> sizeEntries = entry.getValue().getAsJsonObject().entrySet();
                    for(Map.Entry<String, JsonElement> sizeEntry: sizeEntries){

                        newSizeEntries.put(sizeEntry.getKey(), Float.parseFloat(sizeEntry.getValue().toString()));
                    }
                    finalPizzaEntries.put(entry.getKey(), newSizeEntries);
                }
                myMenu.setPizzas(finalPizzaEntries);

                // Populate drinks
                JsonObject drinks = jsonObject.getAsJsonObject("drinks");
                HashMap<String, Float> finalDrinkEntries = new HashMap<String, Float>();
                Set<Map.Entry<String, JsonElement>> drinkEntries = drinks.entrySet();
                for(Map.Entry<String, JsonElement> dEntry: drinkEntries) {
                    finalDrinkEntries.put(dEntry.getKey(), Float.parseFloat(dEntry.getValue().toString()));
                }
                myMenu.setDrinks(finalDrinkEntries);

                // Populate toppings
                JsonArray toppings = jsonObject.getAsJsonArray("toppings");
                List<String> finalToppingList = new ArrayList<String>();
                for(int i = 0; i < toppings.size(); i++){
                    finalToppingList.add(toppings.get(i).getAsString());
                }
                myMenu.setToppings(finalToppingList);
                // Get topping price
                JsonPrimitive toppingPrice = jsonObject.getAsJsonPrimitive("additionalToppingPrice");
                myMenu.setToppingPrice(toppingPrice.getAsFloat());
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return myMenu;
    }
}
