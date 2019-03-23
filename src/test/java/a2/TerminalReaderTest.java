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

import static org.junit.Assert.assertEquals;

public class TerminalReaderTest {
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
        TerminalReader testTerm = new TerminalReader(stream, getEmptyMenu());
        testTerm.startReading();
        assertEquals("/$ Invalid Command", outContent.toString().split("\\n")[2]);
    }

    @Test
    public void initTest() {
        InputStream stream = new ByteArrayInputStream("exit\n".getBytes(StandardCharsets.UTF_8));
        TerminalReader testTerm = new TerminalReader(stream, getEmptyMenu());
        testTerm.startReading();
        assertEquals("/$ ", outContent.toString().split("\\n")[2]);
    }

    @Test
    public void constructorTest() {
        InputStream stream = new ByteArrayInputStream("exit\n".getBytes(StandardCharsets.UTF_8));
        TerminalReader testTerm = new TerminalReader(stream);
        testTerm.startReading();
    }

    @Test
    public void basicConstructorTest() {
        TerminalReader testTerm = new TerminalReader();
    }

    @Test
    public void shallowHelp() {
        InputStream stream = new ByteArrayInputStream("?\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalReader testTerm = new TerminalReader(stream, getEmptyMenu());
        testTerm.startReading();
        assertEquals("/$ \tmenu               \tPrint out the Current Menu", outContent.toString().split("\\n")[2]);
        assertEquals("\tlsorder            \tList all Current Orders and their IDs", outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2]);
    }

    @Test
    public void middleHelp() {
        InputStream stream = new ByteArrayInputStream("neworder\n?\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalReader testTerm = new TerminalReader(stream, getEmptyMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        assertEquals("/Order_0$ \tmenu              \tPrint out the Current Menu", outContent.toString().split("\\n")[3]);
        assertEquals("\t..                \tDeselect Currently Selected Order", outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2]);

    }

    @Test
    public void deepHelp() {
        InputStream stream = new ByteArrayInputStream("neworder\nnewdish drink\n0\nseldish\n0\n?\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalReader testTerm = new TerminalReader(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        assertEquals("\trmdish            \tRemove the Current Dish from the Order", outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 3]);
        assertEquals("\t..                \tDeselect Currently Selected Dish", outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2]);
    }

    @Test
    public void checkOrderNumbering() {
        InputStream stream = new ByteArrayInputStream("neworder\n..\nneworder\nexit\n".getBytes(StandardCharsets.UTF_8));
        TerminalReader testTerm = new TerminalReader(stream, getFakeMenu(), new OrderHandler(), new DeliveryHandler());
        testTerm.startReading();
        String firstOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 2];
        assertEquals("/Order_0$ ", firstOrderLine.substring(0, 10));
        String secondOrderLine = outContent.toString().split("\\n")[outContent.toString().split("\\n").length - 1];
        assertEquals("/Order_1$ ", secondOrderLine);
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
        onePrice.put("Medium", Float.parseFloat("567.0"));
        testPizzas.put("ABCPIZZA", onePrice);
        menu.setPizzas(testPizzas);
        ArrayList<String> toppings = new ArrayList<String>();
        toppings.add("mush");
        menu.setToppings(toppings);
        return menu;
    }

}
