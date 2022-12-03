import static org.junit.jupiter.api.Assertions.*;

import com.example.healtherlogin.plus_minus_weight;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class plus_minus_weightTest extends plus_minus_weight {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testMinus() {
        assertEquals("2.0", plus_minus_weight.minus("4.5"));
        System.out.println(plus_minus_weight.minus("4.5"));
        System.out.println(plus_minus_weight.minus("1.5"));
    }

    @Test
    void testPlus() {
        assertEquals("7.0", plus_minus_weight.plus("4.5"));
        System.out.println(plus_minus_weight.plus("4.5"));

    }
}