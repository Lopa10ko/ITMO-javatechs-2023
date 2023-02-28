package ru.lopa10ko.banks.Transactions;

import ru.lopa10ko.banks.Accounts.Account;
import ru.lopa10ko.banks.Entities.BasicValueAmount;

public class WithdrawTransaction extends OneSidedTransaction {
    public WithdrawTransaction(Account actor, BasicValueAmount value) {
        super(actor, value);
        actor.decreaseMoneyValue(value);
    }
}
