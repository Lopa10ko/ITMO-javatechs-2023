package ru.lopa10ko.banks.Tools;

import java.time.LocalDate;

public class CommissionDayException extends BanksException {
    private CommissionDayException(String errorMessage) {
        super(errorMessage);
    }

    public static CommissionDayException InvalidCommissionDay(int commissionDay, LocalDate currentDate) {
        return new CommissionDayException(String.format("Invalid commission day: %d should be less than %d", commissionDay, currentDate.lengthOfMonth()));
    }
}
