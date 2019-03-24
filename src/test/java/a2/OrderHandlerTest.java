package a2;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class OrderHandlerTest {

  @Test
  public void orderIDStartsZero() {
    OrderHandler testOH = new OrderHandler();
    Order order = testOH.createOrder();
    assertEquals(order.getOrderID(), "0");
  }

  @Test
  public void manyOrderHandlingOrderID() {
    OrderHandler testOH = new OrderHandler();
    for (int i = 0; i < 9999; i++) {
      testOH.createOrder();
    }
    Order order = testOH.createOrder();
    assertEquals(order.getOrderID(), "9999");
  }

  @Test
  public void manyOrderHandlingLastOrder() {
    OrderHandler testOH = new OrderHandler();
    for (int i = 0; i < 9999; i++) {
      testOH.createOrder();
    }
    Order order = testOH.createOrder();
    assertEquals(testOH.getOrder("9999"), order);
  }

  @Test
  public void testGetAllOrders() {
    OrderHandler testOH = new OrderHandler();
    for (int i = 0; i < 9999; i++) {
      testOH.createOrder();
    }
    List<Order> myOrders = testOH.getAllOrders();
    assertEquals(myOrders.size(), 9999);
  }

  @Test
  public void unknownOrder() {
    OrderHandler testOH = new OrderHandler();
    assertNull(testOH.getOrder("JIOFDSOJ"));
  }

  @Test
  public void removeOrder() {
    OrderHandler testOH = new OrderHandler();
    Order order = testOH.createOrder();
    testOH.removeOrder(order);
    assertEquals(testOH.getAllOrders().size(), 0);
  }


}
