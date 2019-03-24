package a2;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Order class. An Order has a distinguishing orderID, a List of Foods in the Order, the
 * total price of the Order, and an associated Delivery.
 */
class Order {

  /**
   * ID of this Order
   */
  private final String orderID;
  /**
   * List of Foods in this Order
   */
  private final List<Food> foods;
  /**
   * Total price of this Order
   */
  private Float totalPrice;
  /**
   * Delivery associated with this Order
   */
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

  /**
   * Getter for Food items in this Order, returns a List of all Food items in this Order.
   *
   * @return List of all Food items in Order
   */
  List<Food> getFoods() {
    return this.foods;
  }

  /**
   * Getter for the Delivery object associated with this Order
   *
   * @return the Delivery object for this Order
   */
  public Delivery getDelivery() {
    return this.delivery;
  }

  /**
   * Getter for this Order's orderID.
   *
   * @return int representing this Order's orderID
   */
  String getOrderID() {
    return this.orderID;
  }

  /**
   * Getter for total price of this Order.
   *
   * @return Float representation of the total price of this Order
   */
  float getPrice() {
    return this.totalPrice;
  }

  /**
   * Setter for this Order's Delivery, takes a Delivery object and sets this Order's delivery field
   * to it.
   *
   * @param delivery the Delivery to set to
   */
  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;

  }

  /**
   * Add food to this order and increment the total price by the total price of the food.
   *
   * @param food the Food to add to this Order
   */
  void addFood(Food food) {
    this.foods.add(food);
    this.totalPrice += food.getPrice();
  }

  /**
   * Remove food from this Order, and subtract food's total price from this Order's total price.
   *
   * @param food the food to remove from this order
   */
  void removeFood(Food food) {
    this.totalPrice -= food.getPrice();
    this.foods.remove(food);
  }

  /**
   * Returns a string representation of this Order, including all foods in this Order and the final
   * price of this Order.
   *
   * @return string representation of this Order
   */
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
