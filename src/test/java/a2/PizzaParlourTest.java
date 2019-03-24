package a2;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PizzaParlourTest {
    @Test
    public void pizzaParlourStartsExits() {
        System.setIn(new ByteArrayInputStream("exit\n".getBytes(StandardCharsets.UTF_8)));
        String[] args = {};
        PizzaParlour.main(args);
    }
}
