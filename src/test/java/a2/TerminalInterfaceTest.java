package a2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TerminalInterfaceTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void fakeTest() {
        assert(true);
    }

    @Test
    public void invalidCommandTest() {
        InputStream stream = new ByteArrayInputStream("hi\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getEmptyMenu());
        testTerm.startReading();
        assertEquals("/$ Invalid Command", outContent.toString().split("\\n")[2]);
    }

    @Test
    public void initTest() {
        InputStream stream = new ByteArrayInputStream("exit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getEmptyMenu());
        testTerm.startReading();
        assertEquals("/$ ", outContent.toString().split("\\n")[2]);
    }

    @Test
    public void constructorTest() {
        InputStream stream = new ByteArrayInputStream("exit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream);
        testTerm.startReading();
    }

    @Test
    public void basicConstructorTest() {
        TerminalInterface testTerm = new TerminalInterface();
    }

    @Test
    public void mainMenuHelp() {
        InputStream stream = new ByteArrayInputStream("?\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getEmptyMenu());
        testTerm.startReading();
        assertEquals("/$ \tmenu               \tPrint out the Current Menu", outContent.toString().split("\\n")[2]);
        assertEquals("\tlsorder            \tList all Current Orders and their IDs", outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2]);
    }

    @Test
    public void orderHelp() {
        InputStream stream = new ByteArrayInputStream("neworder\n?\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getEmptyMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        assertEquals("/Order_0$ \tmenu              \tPrint out the Current Menu", outContent.toString().split("\\n")[3]);
        assertEquals("\t..                \tDeselect Currently Selected Order", outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2]);

    }

    @Test
    public void dishHelp() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewdrink\n0\nseldish\n0\n?\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        assertEquals("\trmdish            \tRemove the Current Dish from the Order", outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 3]);
        assertEquals("\t..                \tDeselect Currently Selected Dish", outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2]);
    }

    @Test
    public void checkOrderHandling() {
        InputStream stream = new ByteArrayInputStream("neworder\n..\nneworder\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2];
        assertEquals("/Order_0$ ", firstOrderLine.substring(0, 10));
        String secondOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 1];
        assertEquals("/Order_1$ ", secondOrderLine);
    }

    @Test
    public void orderSelection() {
        InputStream stream = new ByteArrayInputStream("neworder\n..\nneworder\n..\nselorder 0\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 1];
        assertEquals("/Order_0$ ", firstOrderLine.substring(0, 10));
    }

    @Test
    public void orderSelection2() {
        InputStream stream = new ByteArrayInputStream("neworder\n..\nneworder\n..\nselorder\n1\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 1];
        assertEquals("/Order_1$ ", firstOrderLine.substring(0, 10));
    }

    @Test
    public void orderSelection3() {
        InputStream stream = new ByteArrayInputStream("neworder\n..\nneworder\n..\nselorder 1\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 1];
        assertEquals("/Order_1$ ", firstOrderLine.substring(0, 10));
    }
    @Test
    public void createDrink() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewdrink\n0\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2];
        assertEquals("Drink Successfully Added to Order", firstOrderLine);

    }

    @Test
    public void noOrdersListOrders() {
        InputStream stream = new ByteArrayInputStream("lsorder\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2];
        assertTrue(firstOrderLine.contains("No Current Orders"));
    }
    @Test
    public void createDrinkListDishes() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewdrink\n0\nlsdish\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2];
        assertTrue(firstOrderLine.contains("Drink"));
        // for drink type
        assertTrue(firstOrderLine.contains("COKE"));
    }

    @Test
    public void createDrinkListOrders() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewdrink\n0\n..\nlsorder\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2];
        assertTrue(firstOrderLine.contains("Drink"));
        // for drink type
        assertTrue(firstOrderLine.contains("COKE"));
    }

    @Test
    public void createDrinkCheckDrink() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewdrink\n0\nseldish\n0\nprintdish\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2];
        assertTrue(firstOrderLine.contains("Drink"));
        // for drink type
        assertTrue(firstOrderLine.contains("COKE"));
    }


    @Test
    public void createDrinkChangeDrinkEmptyInput() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewdrink\n0\nseldish\n0\nchdish\n\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2];
        assertEquals("Drink Has Been Modified", firstOrderLine);

    }

    @Test
    public void changeDishInvalidInput() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewdrink\n0\nseldish\n0\nchdish\n-kjjnkdv0\nprintdish\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 3];
        assertEquals("Invalid Option", firstOrderLine);
    }
    @Test
    public void createPizza() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewpizza\n0\n0\n0,0\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2];
        assertEquals("Pizza Successfully Added to Order", firstOrderLine);

    }

    @Test
    public void acceptMalformedToppings() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewpizza\n0\n0\n0,0,-678678,gdfgdf\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 3];
        assertTrue(firstOrderLine.contains("Skipping unknown topping"));
        String secondOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2];
        assertEquals("Pizza Successfully Added to Order", secondOrderLine);

    }

    @Test
    public void createPizzaCheckPizza() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewpizza\n0\n0\n0,0\nseldish\n0\nprintdish\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2];
        assertTrue(firstOrderLine.contains("ABCPIZZA"));
        // for mushroom
        assertTrue(firstOrderLine.contains("+2"));
        assertTrue(firstOrderLine.contains("MUSH"));
    }

    @Test
    public void createThenChangePizza() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewpizza\n0\n0\n0,0\nseldish\n0\nchdish\n\n\n-0,-0\nprintdish\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 3];
        assertEquals("Pizza Has Been Modified", firstOrderLine);
        String secondLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2];
        assertFalse(secondLine.contains("+"));
        assertFalse(secondLine.contains("MUSH"));
        assertFalse(secondLine.contains("mush"));
    }


    @Test
    public void createPizzaPrintOrder() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewpizza\n0\n0\n0,0\nprintorder\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2];
        assertTrue(firstOrderLine.startsWith("Final Price: ($575"));
    }


    @Test
    public void tryDeliver() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewdrink\n0\ndeliver\n0\nstgeorge\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        assertTrue(outContent.toString().contains("Delivery Request has been Created"));
    }

    @Test
    public void tryInvalidDeliver() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewdrink\n0\ndeliver\ngregw\nstgeorge\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        assertTrue(outContent.toString().contains("Invalid Option"));
        assertTrue(outContent.toString().contains("Invalid Command"));
    }

    @Test
    public void stopDeliver() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewdrink\n0\ndeliver\n0\nstgeorge\nrmdeliver\nprintorder\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalInterface testTerm = new TerminalInterface(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        assertTrue(outContent.toString().contains("Delivery Method: Pickup"));
    }
    private Menu getEmptyMenu(){
        return new Menu();
    }
    private Menu getFakeMenu(){
        Menu menu = new Menu();
        HashMap<String,Float> testDrinks = new HashMap<String, Float>();
        testDrinks.put("Coke", Float.parseFloat("3.50"));
        menu.setDrinks(testDrinks);
        HashMap<String, HashMap<String, Float>> testPizzas = new HashMap<>();
        HashMap<String, Float> onePrice = new HashMap<>();
        onePrice.put("m", Float.parseFloat("567.0"));
        testPizzas.put("ABCPIZZA", onePrice);
        menu.setPizzas(testPizzas);
        ArrayList<String> toppings = new ArrayList<String>();
        toppings.add("mush");
        menu.setToppings(toppings);
        menu.setToppingPrice((float) 4.00);
        return menu;
    }

}
