package a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderHandler {
    private HashMap<String, Order> allOrdersMap;
    private int nextOrderID;
    public OrderHandler() {
        this.allOrdersMap = new HashMap<String, Order>();
    }
    public Order createOrder() {
        String nextOrderID = getNextOrderID();
        Order newOrder = new Order(nextOrderID);
        this.allOrdersMap.put(nextOrderID, newOrder);
        return newOrder;
    }
    private String getNextOrderID(){
        String nextOrderID = Integer.toString(this.nextOrderID);
        this.nextOrderID++;
        return nextOrderID;
    }
    public Order getOrder(String orderID) {
        return allOrdersMap.get(orderID);
    }

    public List<Order> getAllOrders() {
        return new ArrayList<Order>(allOrdersMap.values());
    }
    public void removeOrder(Order currOrder) {
        allOrdersMap.remove(currOrder.getOrderID());
        //TODO check for deliveries etc.
    }
}
