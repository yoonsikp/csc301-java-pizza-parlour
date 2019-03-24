package a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Menu {
    private HashMap<String, HashMap<String, Float>> pizzaSet;
    private HashMap<String, Float> drinkSet;
    private List<String> toppingList;
    private Float toppingPrice;


    public List<String> getPizzaTypes(){
        return new ArrayList<String>(pizzaSet.keySet());
    }

    public List<String> getPizzaToppings(){
        return toppingList;
    }

    public List<String> getPizzaSizes(String pizzaType){
        return new ArrayList<String>(pizzaSet.get(pizzaType).keySet());
    }

    public List<String> getDrinks(){
        return new ArrayList<String>(drinkSet.keySet());
    }

    public Float getPizzaPrice(String pizzaType, String pizzaSize){
        return this.pizzaSet.get(pizzaType).get(pizzaSize);
    }

    public Float getDrinkPrice(String currDrink){ return this.drinkSet.get(currDrink); }

    public String getMenuItem(String menuItem){
        StringBuilder menuItemString = new StringBuilder();
        if(this.pizzaSet.containsKey(menuItem)){
            String pizzaString = menuItem + ": ";
            menuItemString.append(pizzaString);
            menuItemString.append(getPizzaSizesAsString(menuItem));

        }else if(this.drinkSet.containsKey(menuItem)){
            String drinkString = menuItem + ": ($" + this.drinkSet.get(menuItem).toString() + ")\n";
            menuItemString.append(drinkString);
        }
        return menuItemString.toString();
    }

    public String getPizzaSizesAsString(String type){
        StringBuilder menuString = new StringBuilder();
        for(String size : this.pizzaSet.get(type).keySet()){
            menuString.append(size);
            menuString.append(" ($");
            menuString.append(this.pizzaSet.get(type).get(size).toString());
            menuString.append(") ");
        }
        return menuString.toString();
    }

    public void setToppings(List<String> toppingList){
        this.toppingList = toppingList;
    }

    public void setPizzas(HashMap<String, HashMap<String, Float>> pizzaSet){
        this.pizzaSet = pizzaSet;
    }

    public void setDrinks(HashMap<String, Float> drinkSet){
        this.drinkSet = drinkSet;
    }

    public String toString() {
        StringBuilder menuString = new StringBuilder();
        menuString.append("Pizzas:\n");
        for(String type: this.pizzaSet.keySet()){
            String pizzaString = "- " + type + " ";
            menuString.append(pizzaString);
            menuString.append(" ");
            menuString.append(getPizzaSizesAsString(type));
            menuString.append("\n");
        }
        menuString.append("Drinks:\n");
        for(String drink : this.drinkSet.keySet()){
            String drinkString = "- " + drink + " ($";
            menuString.append(drinkString);
            menuString.append(this.drinkSet.get(drink).toString());
            menuString.append(")\n");
        }

        return menuString.toString();
    }

    public void setToppingPrice(Float toppingPrice) {
        this.toppingPrice = toppingPrice;
    }

}
