import Property.SimpleTask;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleTaskTest {

    @Test
    public void testSimpleTaskNotifiesWhenCostChanges() {
        SimpleTask task = new SimpleTask(new BigDecimal("100.00"));

        final boolean[] notified = {false};

        task.addObserver(evt -> {
            notified[0] = true;
            assertEquals("cost", evt.getPropertyName());
            assertEquals(new BigDecimal("100.00"), evt.getOldValue());
            assertEquals(new BigDecimal("150.00"), evt.getNewValue());
        });

        task.changeCost(new BigDecimal("150.00"));
        assertTrue(notified[0], "L'observador hauria de ser notificat");
    }
}

