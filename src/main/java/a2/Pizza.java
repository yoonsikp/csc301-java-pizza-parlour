package a2;

import java.util.HashMap;

/**
 * This is the Pizza class.
 * A Pizza is a Food object that has a specific type, size, toppings and price.
 */
class Pizza extends Food {

    /**
     * Size of the pizza
     */
    private final String size;
    /**
     * Toppings on the pizza
     */
    private final HashMap<String, Integer> toppings;

    /**
     * Constructor for a Pizza object, which extends a Food object, uses a Builder design pattern to avoid constructor
     * tunneling. Creates a Pizza with a provided type, size, toppings and price.
     *
     * @param builder builder that makes the Pizza object
     */
    private Pizza(Builder builder) {
        super(builder);
        this.size = builder.size;
        this.toppings = builder.toppings;
    }

    /**
     * Getter for toppings on this Pizza
     *
     * @return list of toppings
     */
    HashMap<String, Integer> getToppings() {
        return this.toppings;
    }

    /**
     * Getter for the size of this pizza
     *
     * @return size of pizza
     */
    String getSize() {
        return this.size;
    }

    /**
     * Return a string representation of a Pizza object.
     *
     * @return string representation of Pizza
     */
    public String toString() {
        StringBuilder pizzaString = new StringBuilder();
        pizzaString.append(this.getType().toUpperCase());
        pizzaString.append(" Pizza with ");
        for (String topping : this.toppings.keySet()) {
            int topNum = this.toppings.get(topping);
            if (topNum != 0) {
                if (topNum > 0) {
                    pizzaString.append("+");
                }
                pizzaString.append(topNum);
                pizzaString.append(" ").append(topping.toUpperCase());
                pizzaString.append(", ");
            }
        }
        pizzaString.append("Size ");
        pizzaString.append(this.size.toUpperCase());
        pizzaString.append(" ($");
        pizzaString.append(this.getPrice().toString());
        pizzaString.append(")");

        return pizzaString.toString();
    }

    /**
     * This is a static Builder class that is used to construct Pizza objects.
     */
    public static class Builder extends Food.FoodBuilder<Builder> {

        /**
         * The size of the builder, i.e. the size to set the Pizza to
         */
        private String size;
        /**
         * The toppings of the builder, i.e. the toppings to set the Pizza to
         */
        private HashMap<String, Integer> toppings;

        /**
         * Returns an instance of the Pizza Builder class.
         *
         * @return a Pizza Builder
         */
        @Override
        protected Builder getThis() {
            return this;
        }

        /**
         * Sets builder's size.
         *
         * @param size size to set to
         * @return current builder instance
         */
        Builder size(String size) {
            this.size = size;
            return this;
        }

        /**
         * Sets builder's toppings.
         *
         * @param toppings toppings to set to
         * @return current builder instance
         */
        Builder toppings(HashMap<String, Integer> toppings) {
            this.toppings = toppings;
            return this;
        }

        /**
         * Build the desired Pizza type depending on the builder's type. Returns an Pizza with size and toppings declared,
         * and returns a Pizza with size m if no size is declared.
         *
         * @return new Pizza object
         */
        public Food build() {
            if (this.size == null) {
                this.size = "m";
            }
            if (this.toppings == null) {
                this.toppings = new HashMap<>();
            }
            return new Pizza(this);
        }
    }
}
