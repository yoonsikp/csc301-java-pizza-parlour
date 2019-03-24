package a2;

import java.util.ArrayList;
import java.util.List;

class Order {

  private final String orderID;
  private final List<Food> foods;
  private Float totalPrice;
  private Delivery delivery;

  /**
   * Constructor for Order, instantiate a new Order with with orderID as ID.
   *
   * @param orderID the ID of the order to instantiate
   */
  public Order(String orderID) {
    this.orderID = orderID;
    this.foods = new ArrayList<>();
    this.totalPrice = (float) 0;
  }

  List<Food> getFoods() {
    return this.foods;
  }

  public Delivery getDelivery() {
    return this.delivery;
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;

  }

  float getPrice() {
    return this.totalPrice;
  }

  void addFood(Food food) {
    this.foods.add(food);
    this.totalPrice += food.getPrice();
  }

  String getOrderID() {
    return this.orderID;
  }

  void removeFood(Food food) {
    this.totalPrice -= food.getPrice();
    this.foods.remove(food);
  }

  public String toString() {
    if (foods.size() == 0) {
      return "No Dishes in Order";
    }
    StringBuilder foodString = new StringBuilder();
    for (Food food : this.foods) {
      foodString.append(food.toString()).append(", ");
    }
    return foodString.append("Final Price: ($").append(this.getPrice()).append(")").toString();
  }
}
