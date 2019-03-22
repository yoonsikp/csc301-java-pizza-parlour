package a2;

import java.util.ArrayList;
import java.util.List;

public class OrderHandler {
    public Order createOrder() {
        return new Order();
    }

    public Order getOrder(String s) {
        return new Order();
    }

    public List<Order> getAllOrders() {
        return new ArrayList<Order>();
    }

    public void removeOrder(Order currOrder) {
    }
}
