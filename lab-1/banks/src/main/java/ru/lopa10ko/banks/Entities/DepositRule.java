package ru.lopa10ko.banks.Entities;


import org.jetbrains.annotations.NotNull;

public record DepositRule(BasicValueAmount criticalValue, BasicValueAmount percent) {
    public DepositRule(double criticalValue, double percent) {
        this(new BasicValueAmount(criticalValue), new BasicValueAmount(percent));
    }

    public boolean isOverlapping(@NotNull DepositRule other) {
        return criticalValue.equals(other.criticalValue);
    }

    public DepositRule getMaxPercent(@NotNull DepositRule other) {
        return percent.getValue() > other.percent.getValue() ? this : other;
    }

    public BasicValueAmount getPercent() {return percent;}
    public BasicValueAmount getCriticalValue() {return criticalValue;}

}
