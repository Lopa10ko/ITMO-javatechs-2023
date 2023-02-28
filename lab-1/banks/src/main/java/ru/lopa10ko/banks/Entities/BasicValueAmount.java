package ru.lopa10ko.banks.Entities;

import org.jetbrains.annotations.NotNull;
import ru.lopa10ko.banks.Tools.ValueAmountException;

import java.util.Objects;

public class BasicValueAmount implements ValueAmount, Comparable<BasicValueAmount> {
    private double value;
    public BasicValueAmount() {this(0);}
    public BasicValueAmount(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {return value;}

    @Override
    public void setValue(double value) {
        this.value = ValidateValue(value);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicValueAmount that = (BasicValueAmount) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {return Objects.hash(value);}

    private static double ValidateValue(double value) throws ValueAmountException {
        if (value < 0)
            throw ValueAmountException.InvalidValueAmount(value);
        return value;
    }

    @Override
    public int compareTo(@NotNull BasicValueAmount o) {
        return Double.compare(value, o.getValue());
    }
}
