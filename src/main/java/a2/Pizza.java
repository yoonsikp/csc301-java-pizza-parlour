package a2;

import java.util.HashMap;

class Pizza extends Food {

    public static class Builder extends Food.FoodBuilder<Builder> {
        @Override protected Builder getThis() {
            return this;
        }
        private String size;
        private HashMap<String,Integer> toppings;
        Builder size(String size){
            this.size = size;
            return this;
        }
        Builder toppings(HashMap<String, Integer> toppings){
            this.toppings = toppings;
            return this;
        }
        public Food build(){
            if (this.size == null) {
                this.size = "M";
            }
            if (this.toppings == null){
                this.toppings = new HashMap<>();
            }
            return new Pizza(this);
        }
    }

    private String size;
    private HashMap<String,Integer> toppings;

    private Pizza(Builder builder){
        super(builder);
        this.size = builder.size;
        this.toppings = builder.toppings;
    }

    HashMap<String,Integer> getToppings() {
        return this.toppings;
    }
    String getSize(){
        return this.size;
    }
    public String toString() {
        StringBuilder pizzaString = new StringBuilder();
        pizzaString.append(this.getType().toUpperCase());
        pizzaString.append(" Pizza with ");
        for (String topping : this.toppings.keySet()) {
            int topNum = this.toppings.get(topping);
            if(topNum != 0) {
                if (topNum > 0) pizzaString.append("+");
                pizzaString.append(topNum);
                pizzaString.append(" ").append(topping.toUpperCase());
                pizzaString.append(", ");
            }
        }
        pizzaString.append("size ");
        pizzaString.append(this.size);
        pizzaString.append(" ($");
        pizzaString.append(this.getPrice().toString());
        pizzaString.append(")");

        return pizzaString.toString();
    }
}
