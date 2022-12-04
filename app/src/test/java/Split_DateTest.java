import static org.junit.jupiter.api.Assertions.*;

import com.example.healtherlogin.Split_Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Split_DateTest extends Split_Date {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void split() {
        assertArrayEquals(new int[]{2022, 12, 17},Split_Date.split("2022,12,19"));
    }

    @Test
    void restore() {
        assertEquals("2022,12,9",Split_Date.restore(2022,12,9));
    }
}