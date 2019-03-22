package a2;

import java.util.ArrayList;
import java.util.List;

public class Order {
    String orderID;
    List<Food> foods;
    public Order (String orderID){
        this.orderID = orderID;
        this.foods = new ArrayList<Food>();
    }
    public void setDelivery(Delivery delivery) {

    }

    public List<Food> getFoods() {
        return this.foods;
    }

    public Delivery getDelivery() {
        return new Delivery();
    }

    public String getPrice() {
        return "3.20";
    }

    public void addFood(Food food) {
        this.foods.add(food);
    }

    public String getOrderID() {
        return this.orderID;
    }

    public void removeFood(Food food) {
        this.foods.remove(food);
    }
}
