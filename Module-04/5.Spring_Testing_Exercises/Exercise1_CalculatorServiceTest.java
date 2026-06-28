import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorServiceTest {
    CalculatorService service = new CalculatorService();

    @Test
    void testAdd() {
        assertEquals(15, service.add(10,5));
        assertEquals(0, service.add(-5,5));
    }
}