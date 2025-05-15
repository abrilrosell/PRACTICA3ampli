package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Task {
    protected BigDecimal cost;

    public Task(BigDecimal cost) {
        this.cost = cost.setScale(2, RoundingMode.HALF_UP);
        if (this.cost.signum() <= 0) {
            throw new IllegalArgumentException("Cost must be positive");
        }
    }

    public final BigDecimal costInEuros() {
        return this.cost;
    }
}

