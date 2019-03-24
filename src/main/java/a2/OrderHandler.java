package a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderHandler {

  private HashMap<String, Order> allOrdersMap;
  private int nextOrderID;

  /**
   * Constructor for OrderHandler
   */
  public OrderHandler() {
    this.allOrdersMap = new HashMap<String, Order>();
  }

  /**
   * Creates the order with the nextOrderID and adds it to the HashMap of all orders.
   *
   * @return newly created order
   */
  public Order createOrder() {
    String nextOrderID = getNextOrderID();
    Order newOrder = new Order(nextOrderID);
    this.allOrdersMap.put(nextOrderID, newOrder);
    return newOrder;
  }

  /**
   * Get the orderID to be used for the next order and then increment nextOrderID in preparation for
   * the next order.
   *
   * @return ID to use for the next order
   */
  private String getNextOrderID() {
    String nextOrderID = Integer.toString(this.nextOrderID);
    this.nextOrderID++;
    return nextOrderID;
  }

  /**
   * Get the order in allOrdersMap with the provided ID.
   *
   * @param orderID the ID of the desired order
   * @return return the order with the orderID
   */
  public Order getOrder(String orderID) {
    if (allOrdersMap.containsKey(orderID)) {
      return allOrdersMap.get(orderID);
    } else {
      return null;
    }
  }

  /**
   * Return an ArrayList of all orders in allOrdersMap
   *
   * @return an ArrayList of all orders
   */
  public List<Order> getAllOrders() {
    return new ArrayList<Order>(allOrdersMap.values());
  }

  /**
   * Remove currOrder from the running list of all orders, allOrdersMap.
   *
   * @param currOrder the order to remove
   */
  public void removeOrder(Order currOrder) {
    allOrdersMap.remove(currOrder.getOrderID());
  }
}
