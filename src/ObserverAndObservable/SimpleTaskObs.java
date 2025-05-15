package ObserverAndObservable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Observer;

public class SimpleTaskObs extends Task {
    private boolean changed = false; // Indica si el cost ha canviat realment
    private Observer observer; // Un únic observador registrat (la CompositeTask que la conté)


    // Constructor: rep el cost inicial i el valida via superclasse
    public SimpleTaskObs(BigDecimal cost) {
        super(cost);
    }

    // Mètode per canviar el cost de la tasca
    public void changeCost(BigDecimal newCost) {
        BigDecimal formatted = newCost.setScale(2, RoundingMode.HALF_UP); // Normalitzem el nou cost a 2 decimals amb arrodoniment
        if (formatted.signum() <= 0) {
            throw new IllegalArgumentException("Cost must be positive");
        }

        // Només actualitzem si el cost realment ha canviat
        if (!this.cost.equals(formatted)) {
            BigDecimal oldCost = this.cost;
            this.cost = formatted;
            // Marquem com a canviat i notifiquem a l’observador
            setChanged();
            notifyObservers(new CostChanged(oldCost, formatted));
        }
    }

    // Marca que hi ha hagut un canvi real
    protected void setChanged() {
        changed = true;
    }

    // Notifica l’observador registrat, passant un objecte amb el canvi
    protected void notifyObservers(Object arg) {
        if (changed && observer != null) {
            // Avisem l’observador (composite), passant-li aquest canvi
            observer.update(null, arg);
        }
        changed = false; // Reiniciem l’estat
    }

    // Registra un observador (normalment una CompositeTaskObs)
    public void addObserver(Observer o) {
        this.observer = o;
    }
}
