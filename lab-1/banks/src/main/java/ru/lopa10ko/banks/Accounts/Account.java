package ru.lopa10ko.banks.Accounts;

import ru.lopa10ko.banks.Entities.BasicValueAmount;
import ru.lopa10ko.banks.Transactions.Transaction;

import java.util.ArrayList;
import java.util.UUID;

public interface Account {
    void increaseMoneyValue(BasicValueAmount value);
    void decreaseMoneyValue(BasicValueAmount value);
    void remitTo(Account account, BasicValueAmount value);
    int getCommissionDay();
    void removeWithdrawLimit();
    void evaluateCommission();
    void accrueCommission();
    void addTransaction(Transaction transaction);
    ArrayList<Transaction> getTransactions();
    UUID getId();
    String toString();
}
