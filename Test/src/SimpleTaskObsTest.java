import ObserverAndObservable.SimpleTaskObs;
import model.CostChanged;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleTaskObsTest {

    @Test
    public void testObserverReceivesNotificationOnCostChange() {
        SimpleTaskObs task = new SimpleTaskObs(new BigDecimal("100"));

        final boolean[] notified = {false};
        final CostChanged[] receivedChange = {null};

        task.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                notified[0] = true;
                receivedChange[0] = (CostChanged) arg;
            }
        });

        task.changeCost(new BigDecimal("150"));

        assertTrue(notified[0], "L'observador hauria de rebre la notificació");
        assertNotNull(receivedChange[0], "El canvi hauria d'estar informat");
        assertEquals(new BigDecimal("100.00"), receivedChange[0].oldCost());
        assertEquals(new BigDecimal("150.00"), receivedChange[0].newCost());
    }

    @Test
    public void testNoNotificationIfCostUnchanged() {
        SimpleTaskObs task = new SimpleTaskObs(new BigDecimal("100"));

        final boolean[] notified = {false};

        task.addObserver((o,arg) -> notified[0] = true);

        task.changeCost(new BigDecimal("100")); // mateix cost

        assertFalse(notified[0], "No hauria de notificar si el cost no canvia");
    }
}
