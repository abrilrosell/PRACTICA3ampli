package Property;

import model.Task;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CompositeTask extends Task implements PropertyChangeListener {

    // Llista de subtasques (simples o compostes)
    private final List<Task> subtasks;

    // Eina per gestionar observadors que volen escoltar canvis d’aquesta composite
    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    // Constructor: rep la llista de subtasques i fa una còpia privada
    public CompositeTask(List<Task> subtasks) {
        super(BigDecimal.ZERO);
        this.subtasks = new ArrayList<>(subtasks); // Còpia per protegir l’estat intern
        subscribeToSubtasks(); // Ens registrem com a observadors de les subtasques
        recalculateCost(); // Calculem el cost inicial sumant les subtasques
    }

    // Ens registrem com a observadors de cada subtasca
    private void subscribeToSubtasks() {
        for (int i = 0; i < subtasks.size(); i++) {
            Task t = subtasks.get(i);

            if (t instanceof SimpleTask s) {
                s.addObserver(this); // Ens subscrivim a la simple
            } else if (t instanceof CompositeTask c) {
                c.addObserver(this); // Ens subscrivim a una altra composite
            }
        }
    }

    // Recalcula el cost total sumant el cost actual de totes les subtasques
    private void recalculateCost() {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < subtasks.size(); i++) {
            Task t = subtasks.get(i);
            total = total.add(t.costInEuros());
        }
        this.cost = total.setScale(2, RoundingMode.HALF_UP);
    }

    // Aquest mètode es crida automàticament quan una subtasca notifica un canvi
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        BigDecimal oldCost = this.cost;
        recalculateCost(); // Recalcula el cost total en funció de les noves dades
        // Només notifiquem si el cost realment ha canviat
        if (!oldCost.equals(this.cost)) {
            changeSupport.firePropertyChange("cost", oldCost, this.cost);
        }
    }

    // Permet a una altra classe registrar-se com a observadora d’aquesta composite
    public void addObserver(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    // Permet eliminar un observador registrat
    public void removeObserver(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
}

