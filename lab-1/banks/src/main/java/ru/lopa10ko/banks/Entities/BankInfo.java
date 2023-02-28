package ru.lopa10ko.banks.Entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import ru.lopa10ko.banks.Tools.ValueAmountException;

@Data
public class BankInfo {
    @Setter(AccessLevel.NONE)
    private int commissionDay;
    @Setter(AccessLevel.NONE)
    private BasicValueAmount freezeTime;
    @Setter(AccessLevel.NONE)
    private BasicValueAmount debitPercent;
    @Setter(AccessLevel.NONE)
    private BasicValueAmount creditLimit;
    @Setter(AccessLevel.NONE)
    private BasicValueAmount creditCommission;
    @Setter(AccessLevel.NONE)
    private BasicValueAmount untrustedUserWithdrawLimit;
    @Setter(AccessLevel.NONE)
    private DepositRules depositRules;

    public BankInfo(int commissionDay, long freezeTime, double debitPercent, double creditLimit, double creditCommission, double untrustedUserWithdrawLimit, DepositRules depositRules) {
        this.commissionDay = validateCommissionPeriod(commissionDay);
        this.freezeTime = new BasicValueAmount(freezeTime);
        this.debitPercent = new BasicValueAmount(debitPercent);
        this.creditLimit = new BasicValueAmount(creditLimit);
        this.creditCommission = new BasicValueAmount(creditCommission);
        this.untrustedUserWithdrawLimit = new BasicValueAmount(untrustedUserWithdrawLimit);
        this.depositRules = depositRules;
    }

    private static int validateCommissionPeriod(int value) throws ValueAmountException {
        if (value > 0)
            return value;
        throw ValueAmountException.InvalidValueAmount(value);
    }
}
