import Property.CompositeTask;
import Property.SimpleTask;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CompositeTaskTest {

    @Test
    public void testCompositeTaskRecalculatesCost() {
        SimpleTask t1 = new SimpleTask(new BigDecimal("30.00"));
        SimpleTask t2 = new SimpleTask(new BigDecimal("70.00"));
        CompositeTask comp = new CompositeTask(List.of(t1, t2));

        assertEquals(new BigDecimal("100.00"), comp.costInEuros());

        t1.changeCost(new BigDecimal("40.00"));
        assertEquals(new BigDecimal("110.00"), comp.costInEuros());
    }
}