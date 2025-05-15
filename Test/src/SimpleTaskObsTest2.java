import ObserverAndObservable.SimpleTaskObs;
import model.CostChanged;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Observer;
import java.util.Observable;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleTaskObsTest2 {

    @Test
    public void testSimpleTaskObsNotifiesChange() {
        SimpleTaskObs task = new SimpleTaskObs(new BigDecimal("100.00"));

        final boolean[] notified = {false};
        final CostChanged[] result = {null};

        task.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                notified[0] = true;
                result[0] = (CostChanged) arg;
            }
        });

        task.changeCost(new BigDecimal("150.00"));

        assertTrue(notified[0]);
        assertNotNull(result[0]);
        assertEquals(new BigDecimal("100.00"), result[0].oldCost());
        assertEquals(new BigDecimal("150.00"), result[0].newCost());
    }
}
