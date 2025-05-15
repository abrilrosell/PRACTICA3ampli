import ObserverAndObservable.CompositeTaskObs;
import ObserverAndObservable.SimpleTaskObs;
import model.CostChanged;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Observer;
import java.util.Observable;

import static org.junit.jupiter.api.Assertions.*;

public class CompositeTaskObsTest {

    @Test
    public void testCompositeTaskObsReactsToSubtaskChange() {
        SimpleTaskObs t1 = new SimpleTaskObs(new BigDecimal("60.00"));
        SimpleTaskObs t2 = new SimpleTaskObs(new BigDecimal("40.00"));
        CompositeTaskObs comp = new CompositeTaskObs(List.of(t1, t2));

        final boolean[] notified = {false};

        comp.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                notified[0] = true;
                CostChanged change = (CostChanged) arg;
                assertEquals(new BigDecimal("100.00"), change.oldCost());
                assertEquals(new BigDecimal("120.00"), change.newCost());
            }
        });

        t1.changeCost(new BigDecimal("80.00"));

        assertTrue(notified[0], "La composite hauria de rebre la notificació del canvi de cost");
    }
}
