package a2;

import java.util.ArrayList;

class Pizza extends Food {
    private String size;
    private ArrayList<String> toppings;
    public Pizza(){
        this.toppings = new ArrayList<>();
    }

    public void setSize(String pizzaSize) {
        this.size = pizzaSize;
    }

    public void removeTopping(String topping) { this.toppings.remove(topping); }

    public void addTopping(String topping) { this.toppings.add(topping); }

    public String getSize() {
        return this.size;
    }

    public ArrayList<String> getToppings() {
        return this.toppings;
    }
    public String toString() {
        StringBuilder pizzaString = new StringBuilder();
        pizzaString.append(this.getType());
        pizzaString.append(" pizza with ");
        for (String topping : this.toppings) {
            pizzaString.append(topping);
            pizzaString.append(", ");
        }
        pizzaString.append("size ");
        pizzaString.append(this.size);
        pizzaString.append(" (");
        pizzaString.append(this.getPrice().toString());
        pizzaString.append(")");

        return pizzaString.toString();
    }
}
