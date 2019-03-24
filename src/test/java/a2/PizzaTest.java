package a2;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PizzaTest {

    @Test
    public void pizzaType() {
        assertEquals(getPeppPizza().getType(), "PEPP");
    }

    @Test
    public void pizzaPrice() {
        assertEquals(getPeppPizza().getPrice(), (float) 3.20, 0.01);
    }

    @Test
    public void defaultPizzaSize() {
        assertEquals(getPeppPizza().getSize(), "M");
    }

    @Test
    public void defaultPizzaToppings() {
        assertTrue(getPeppPizza().getToppings().size() == 0);
    }

    @Test
    public void pizzaStringWithoutToppings() {
         assertEquals(getPeppPizza().toString().substring(0,4), "PEPP Pizza".substring(0,4));
    }
    @Test
    public void completePizzaString() {
        HashMap<String,Integer> oneToppingMap = new HashMap<>();
        oneToppingMap.put("ONIONS",5);
        Pizza myPizza = (Pizza) new Pizza.Builder().price((float) 3.20).type("PEPP").size("L").toppings(oneToppingMap).build();
        assertTrue(myPizza.toString().startsWith("PEPP Pizza with +5 ONIONS"));
    }

    private Pizza getPeppPizza() {
        Pizza myPizza = (Pizza) new Pizza.Builder().price((float) 3.20).type("PEPP").build();
        return myPizza;
    }

}
