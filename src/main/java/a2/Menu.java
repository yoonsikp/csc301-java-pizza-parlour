package a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Menu {
    private HashMap<String, HashMap<String, Float>> pizzaSet;
    private HashMap<String, Float> drinkSet;
    private List<String> toppingList;


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

    public void setToppings(List<String> toppingList){
        this.toppingList = toppingList;
    }

    public void setPizzas(HashMap<String, HashMap<String, Float>> pizzaSet){
        this.pizzaSet = pizzaSet;
    }

    public void setDrinks(HashMap<String, Float> drinkSet){
        this.drinkSet = drinkSet;
    }
}
