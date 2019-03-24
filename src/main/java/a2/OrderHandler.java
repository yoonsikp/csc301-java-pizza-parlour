package a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is the OrderHandler class. It makes the interface between Orders and the pizza parlour
 * program more transparent and easy to use.
 */
class OrderHandler {

  /**
   * HashMap of all orders in the pizza parlour
   */
  private final HashMap<String, Order> allOrdersMap;
  /**
   * OrderID to set the next Order to
   */
  private int nextOrderID;

  /**
   * Constructor for OrderHandler
   */
  OrderHandler() {
    this.allOrdersMap = new HashMap<>();
  }

  /**
   * Creates the order with the nextOrderID and adds it to the HashMap of all orders.
   *
   * @return newly created order
   */
  Order createOrder() {
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
  Order getOrder(String orderID) {
    return allOrdersMap.getOrDefault(orderID, null);
  }

  /**
   * Return an ArrayList of all orders in allOrdersMap
   *
   * @return an ArrayList of all orders
   */
  List<Order> getAllOrders() {
    return new ArrayList<>(allOrdersMap.values());
  }

  /**
   * Remove currOrder from the running list of all orders, allOrdersMap.
   *
   * @param currOrder the order to remove
   */
  void removeOrder(Order currOrder) {
    allOrdersMap.remove(currOrder.getOrderID());
  }
}
