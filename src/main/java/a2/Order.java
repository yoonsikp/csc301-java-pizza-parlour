package a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {
    String orderID;
    List<Food> foods;
    Float totalPrice;
    Delivery delivery;

    /**
     * Constructor for Order, instantiate a new Order with with orderID as ID.
     * @param orderID the ID of the order to instantiate
     */
    public Order (String orderID){
        this.orderID = orderID;
        this.foods = new ArrayList<Food>();
        this.totalPrice = (float) 0;
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;

    }

    public List<Food> getFoods() {
        return this.foods;
    }

    public Delivery getDelivery() {
        return this.delivery;
    }

    public float getPrice() {
        return this.totalPrice;
    }

    public void addFood(Food food) {
        this.foods.add(food);
        this.addToPrice(food.getPrice());
    }

    public String getOrderID() {
        return this.orderID;
    }

    public void removeFood(Food food) {
        this.foods.remove(food);
    }
    public void addToPrice(Float price){ this.totalPrice += price; }

    public String toString(){

        List<Food> foods = this.getFoods();
        StringBuilder foodString = new StringBuilder();
        for (Food food: foods){
            foodString.append(food.toString() + ", ");
        }

        foodString.append("Final Price: " + this.getPrice());

        return foodString.toString();
    }
}
