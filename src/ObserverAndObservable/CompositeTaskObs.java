package ObserverAndObservable;

import model.CostChanged;
import model.Task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class CompositeTaskObs extends Task implements Observer {
    private final List<Task> subtasks; // Llista de tasques (simples o compostes) que componen aquesta
    private Observer parent; // Tasca superior que escolta aquesta, si n'hi ha


    // Constructor: rep una llista de subtasques i en fa una còpia privada
    public CompositeTaskObs(List<Task> subtasks) {
        super(BigDecimal.ONE);
        this.subtasks = new ArrayList<>(subtasks); // Fem còpia per protegir l’estat intern
        subscribeToSubtasks(); // Ens registrem com a observadors de les subtasques
        recalculateCost(); // Calculem el cost inicial sumant les subtasques
    }

    // Registrem aquesta tasca com a observadora de cadascuna de les subtasques
    private void subscribeToSubtasks() {
        for (int i = 0; i < subtasks.size(); i++) {
            Task t = subtasks.get(i);

            if (t instanceof SimpleTaskObs s) {
                s.addObserver(this); // Ens subscrivim a la simple
            } else if (t instanceof CompositeTaskObs c) {
                c.addObserver(this);  // Ens subscrivim a una altra composite (recursivitat)
            }
        }
    }

    // Recalcula el cost total sumant els costos actuals de les subtasques
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
    public void update(Observable o, Object arg) {
        if (arg instanceof CostChanged change) {
            BigDecimal old = this.cost;
            recalculateCost(); // Actualitzem el cost propi en funció de les subtasques

            // Si el nou cost és diferent, notifiquem la tasca que ens conté (si n’hi ha)
            if (!old.equals(this.cost)) {
                notifyParent(old, this.cost); //
            }
        }
    }


    // Ens permet registrar un observador (una tasca superior o GUI)
    public void addObserver(Observer o) {
        this.parent = o;
    }

    // Si tenim una tasca superior registrada, l’informem que el nostre cost ha canviat
    private void notifyParent(BigDecimal oldCost, BigDecimal newCost) {
        if (parent != null) {
            // Notifiquem el pare amb el cost anterior i el nou per tal que pugui reaccionar correctament
            parent.update(null, new CostChanged(oldCost, newCost));
        }
    }
}
