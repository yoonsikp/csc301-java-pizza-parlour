package a2;

import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * This is the MenuLoader class.
 * The MenuLoader is used to read from a JSON file that stores persistent data about the menu available in the
 * pizza parlour, and creates and populates a Menu object that contains all that information.
 */
class MenuLoader {

    /**
     * Creates and populates a Menu object by reading a JSON file and parsing it using GSON library. Populates the '
     * menu with all items on the menu and returns it.
     *
     * @param filename the JSON file storing the menu data
     * @return a Menu containing all data from the JSON
     */
    static Menu getPopulatedMenu(String filename) {
        Menu myMenu = new Menu();
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonTree = parser.parse(new FileReader(filename));
            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();

                // Populate pizzas
                JsonObject pizzas = jsonObject.getAsJsonObject("pizzas");
                Set<Map.Entry<String, JsonElement>> pizzaEntries = pizzas.entrySet();
                HashMap<String, HashMap<String, Float>> finalPizzaEntries = new HashMap<>();
                for (Map.Entry<String, JsonElement> entry : pizzaEntries) {
                    HashMap<String, Float> newSizeEntries = new HashMap<>();
                    Set<Map.Entry<String, JsonElement>> sizeEntries = entry.getValue().getAsJsonObject()
                            .entrySet();
                    for (Map.Entry<String, JsonElement> sizeEntry : sizeEntries) {

                        newSizeEntries.put(sizeEntry.getKey().toLowerCase(),
                                Float.parseFloat(sizeEntry.getValue().toString()));
                    }
                    finalPizzaEntries.put(entry.getKey().toLowerCase(), newSizeEntries);
                }
                myMenu.setPizzas(finalPizzaEntries);

                // Populate drinks
                JsonObject drinks = jsonObject.getAsJsonObject("drinks");
                HashMap<String, Float> finalDrinkEntries = new HashMap<>();
                Set<Map.Entry<String, JsonElement>> drinkEntries = drinks.entrySet();
                for (Map.Entry<String, JsonElement> dEntry : drinkEntries) {
                    finalDrinkEntries
                            .put(dEntry.getKey().toLowerCase(), Float.parseFloat(dEntry.getValue().toString()));
                }
                myMenu.setDrinks(finalDrinkEntries);

                // Populate toppings
                JsonArray toppings = jsonObject.getAsJsonArray("toppings");
                List<String> finalToppingList = new ArrayList<>();
                for (int i = 0; i < toppings.size(); i++) {
                    finalToppingList.add(toppings.get(i).getAsString().toLowerCase());
                }
                myMenu.setToppings(finalToppingList);

                // Get topping price
                JsonPrimitive toppingPrice = jsonObject.getAsJsonPrimitive("toppingPrice");
                myMenu.setToppingPrice(toppingPrice.getAsFloat());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return myMenu;
    }
}
