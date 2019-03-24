package a2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeliveryHandlerTest {
    private final DeliveryHandler deliveryHandler = new DeliveryHandler();

    @Test
    public void fakeTest() {
        assert(true);
    }

    @Test void printDelivDetailsTest(){
        Order emptyOrder = new Order("1");
        assertEquals(this.deliveryHandler.printDeliveryDetails(emptyOrder),
                "You must order at least one item first.");
        Order nonEmptyOrder = createSingleDrinkOrder();
        // no delivery added
        assertEquals(this.deliveryHandler.printDeliveryDetails(nonEmptyOrder), "Your order is for pick-up.");

        // test ubereats JSON
        Delivery delivUbereats = this.deliveryHandler.createDelivery(nonEmptyOrder, "unhappy", "ubereats");
        nonEmptyOrder.setDelivery(delivUbereats);
        assertEquals(3, this.deliveryHandler.printDeliveryDetails(nonEmptyOrder).length());

        // test foodora CSV
        Delivery delivFoodora = this.deliveryHandler.createDelivery(nonEmptyOrder, "unhappy", "foodora");
        nonEmptyOrder.setDelivery(delivFoodora);
        assertEquals("Address,Order Details,Order Number\ngrht,COLA Drink ($1.5),0",
                this.deliveryHandler.printDeliveryDetails(nonEmptyOrder));


    }




   private Order createSingleDrinkOrder(){
       Order order = new Order("2");
       Food currFood = new Drink.Builder()
               .type("cola")
               .price((float) 3.3)
               .build();
       order.addFood(currFood);
       return order;

   }
}
