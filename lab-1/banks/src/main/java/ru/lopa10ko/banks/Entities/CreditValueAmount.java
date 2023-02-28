package ru.lopa10ko.banks.Entities;

public class CreditValueAmount implements ValueAmount{
    private double value;
    public CreditValueAmount() {this(0);}
    public CreditValueAmount(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {return value;}

    @Override
    public void setValue(double value) {
        this.value = value;}
}
