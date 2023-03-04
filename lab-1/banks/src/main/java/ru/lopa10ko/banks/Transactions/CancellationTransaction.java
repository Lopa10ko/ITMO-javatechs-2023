package ru.lopa10ko.banks.Transactions;

import ru.lopa10ko.banks.Accounts.Account;
import ru.lopa10ko.banks.Entities.BasicValueAmount;

public class CancellationTransaction extends BasicTransaction {
    public CancellationTransaction(Account actor, Account recipient, BasicValueAmount value) {
        super(actor, recipient, value);
        actor.remitTo(recipient, value);
    }
}
