package a2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class OrderTest {

  @Test
  public void orderIDTest() {
    Order testOrder = new Order("23ABC");

    assertEquals(testOrder.getOrderID(), "23ABC");
  }


  @Test
  public void orderPriceTest() {
    Order testOrder = new Order("23ABC");
    testOrder.addFood(new Drink.Builder().price((float) 3.20).build());
    assertEquals(testOrder.getPrice(), (float) 3.20, 0.01);
  }


  @Test
  public void orderRemovePriceTest() {
    Order testOrder = new Order("23ABC");
    testOrder.addFood(new Drink.Builder().price((float) 3.20).build());
    testOrder.removeFood(testOrder.getFoods().get(0));
    assertEquals(testOrder.getPrice(), (float) 0, 0.01);
  }

  @Test
  public void holdsDelivery() {
    Order testOrder = new Order("23ABC");
    Delivery test = new Delivery.Builder().type("ubereats").build();
    testOrder.setDelivery(test);
    assertEquals(testOrder.getDelivery(), test);
  }

  @Test
  public void emptyOrderString() {
    Order testOrder = new Order("23ABC");

    assertEquals(testOrder.toString(), "No Dishes in Order");
  }

  @Test
  public void notEmptyOrderString() {
    Order testOrder = new Order("23ABC");
    testOrder.addFood(new Drink.Builder().type("drink").price((float) 3.20).build());
    assertNotEquals(testOrder.toString(), "No Dishes in Order");
  }


}
