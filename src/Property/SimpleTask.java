package Property;

import model.Task;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;

// Classe que representa una tasca simple amb cost modificable.
// Implementa el patró Observer utilitzant PropertyChangeSupport.
public class SimpleTask extends Task {
    // Objecte auxiliar que gestiona la llista d’observadors
    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    // Constructor: rep un cost inicial, i es valida i normalitza al constructor de Task
    public SimpleTask(BigDecimal cost) {
        super(cost);
    }

    // Mètode per canviar el cost de la tasca
    public void changeCost(BigDecimal newCost) {
        BigDecimal roundedCost = newCost.setScale(2, BigDecimal.ROUND_HALF_UP);
        if (roundedCost.signum() <= 0) {
            throw new IllegalArgumentException("Cost must be positive");
        }

        // Només fem el canvi si el nou cost és diferent
        if (!this.cost.equals(roundedCost)) {
            BigDecimal oldCost = this.cost;
            this.cost = roundedCost;
            changeSupport.firePropertyChange("cost", oldCost, roundedCost); // Notifiquem els observadors del canvi de cost
        }
    }

    // Afegeix un observador a la llista d’observadors
    public void addObserver(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    // Elimina un observador registrat anteriorment
    public void removeObserver(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
}
