package a2;

class Pizza extends Food {
    private String size;
    private String toppings;

    public void setSize(String pizzaSize) {
        this.size = pizzaSize;
    }

    public void removeTopping(String s) {
    }

    public void addTopping(String s) {
    }

    public String getSize() {
        return this.size;
    }

    public String getToppings() {
        return toppings;
    }
    public String toString() {
        return "I'm a pizza";
    }
}
