package a2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderTest {

    @Test
    public void orderIDTest() {
        Order testOrder = new Order("23ABC");

        assertEquals(testOrder.getOrderID(),"23ABC");
    }

}
