import Exceptions.NotZeroOrNegativeException;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class Vegetable implements Comparable<Vegetable>, Serializable {
    @Getter
    private Vegetables type; // Value from enum
    @Getter
    private double weight; // Weight in gr
    @Getter
    private double calories; // Count of calories on 100 gr

    public Vegetable(Vegetables type, double weight, double calories) throws NotZeroOrNegativeException {
        this.type = type;
        if (weight <= 0 || calories <= 0) {
            throw new NotZeroOrNegativeException("Weight or calories must be greater than zero");
        }
        this.weight = weight;
        this.calories = calories;
    }

    public double getTotalCalories() {
        return calories * (weight / 100.0);
    }
    /* Compare vegetables by calories */
    @Override
    public int compareTo(Vegetable vegetable) {
        return (int) (this.getCalories() - vegetable.getCalories());
    }
}
