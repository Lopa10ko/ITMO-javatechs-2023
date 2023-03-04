package ru.lopa10ko.banks.Accounts;

import lombok.Getter;
import ru.lopa10ko.banks.Entities.BankInfo;
import ru.lopa10ko.banks.Entities.BasicValueAmount;
import ru.lopa10ko.banks.Entities.CreditValueAmount;
import ru.lopa10ko.banks.Transactions.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreditAccount implements Account {
    @Getter
    private final UUID id;
    private final List<Transaction> transactions;
    private CreditValueAmount moneyBank;
    private BasicValueAmount commission;
    private BankInfo bankInfo;
    private boolean isVerified;

    public CreditAccount(CreditValueAmount balance, BankInfo bankInfo, boolean verifiedStatus) {
        this.id = UUID.randomUUID();
        this.transactions = new ArrayList<>();
        this.moneyBank = balance;
        this.commission = new BasicValueAmount();
        this.bankInfo = bankInfo;
        this.isVerified = verifiedStatus;
    }

    @Override
    public void increaseMoneyValue(BasicValueAmount value) {
        moneyBank.setValue(moneyBank.getValue() + value.getValue());
    }

    @Override
    public void decreaseMoneyValue(BasicValueAmount value) {
        moneyBank.setValue(moneyBank.getValue() - validateValue(value.getValue()));
    }

    @Override
    public void remitTo(Account account, BasicValueAmount value) {
        decreaseMoneyValue(value);
        account.increaseMoneyValue(value);
    }

    @Override
    public int getCommissionDay() {
        return bankInfo.getCommissionDay();
    }

    @Override
    public void removeWithdrawLimit() {
        isVerified = true;
    }

    @Override
    public void evaluateCommission() {
        commission.setValue(commission.getValue() + validateCreditValue());
    }

    @Override
    public void accrueCommission() {
        increaseMoneyValue(commission);
        commission.setValue(0);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public ArrayList<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    private double validateValue(double value) {
        return !isVerified ? Double.min(bankInfo.getUntrustedUserWithdrawLimit().getValue(), value) : value;
    }

    private double validateCreditValue() {
        return moneyBank.getValue() < 0 ? bankInfo.getCreditCommission().getValue() : 0;
    }

    @Override
    public String toString() {
        return "CreditAccount{" +
                "id=" + id +
                ", moneyBank=" + moneyBank.getValue() +
                ", isVerified=" + isVerified +
                '}';
    }
}
