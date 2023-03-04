package ru.lopa10ko.banks.Transactions;

import ru.lopa10ko.banks.Accounts.Account;
import ru.lopa10ko.banks.Entities.BasicValueAmount;

public class ReplenishmentTransaction extends OneSidedTransaction {
    public ReplenishmentTransaction(Account actor, BasicValueAmount value) {
        super(actor, value);
        actor.increaseMoneyValue(value);
    }
}
