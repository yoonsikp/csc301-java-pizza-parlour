package a2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeliveryHandlerTest {
    private final DeliveryHandler deliveryHandler = new DeliveryHandler();

    @Test
    public void fakeTest() {
        assert(true);
    }

    @Test void printDelivDetails(){
        Food currFood = new Drink.Builder()
                .type("cola")
                .price((float) 3.3)
                .build();
        Order order = new Order("1");
        order.addFood(currFood);
        // no delivery added
        assertEquals(this.deliveryHandler.printDeliveryDetails(order), "order is for pick-up");

        Delivery deliv = this.deliveryHandler.createDelivery(order, "unhappy", "ubereats");
        order.setDelivery(deliv);


    }




    @Test
    public void createHouseDeliveryTest(){

    }

}
