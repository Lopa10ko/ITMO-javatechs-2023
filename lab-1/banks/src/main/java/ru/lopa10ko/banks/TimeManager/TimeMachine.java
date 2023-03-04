package ru.lopa10ko.banks.TimeManager;


import lombok.Getter;
import ru.lopa10ko.banks.Banks.Bank;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TimeMachine {
    @Getter
    private LocalDate dateTime;
    @Getter
    private final UUID id;
    private static volatile TimeMachine timeMachineInstance;
    private final List<Bank> banks;

    private TimeMachine(UUID id) {
        this.id = id;
        this.dateTime = LocalDate.now();
        banks = new ArrayList<>();
    }

    public static TimeMachine getInstance() {
        TimeMachine localInstance = timeMachineInstance;
        if (localInstance == null) {
            synchronized (TimeMachine.class) {
                localInstance = timeMachineInstance;
                if (localInstance == null) {
                    timeMachineInstance = localInstance = new TimeMachine(UUID.randomUUID());
                }
            }
        }
        return localInstance;
    }

    public void skipDays(int amount) {
        for (int i = 0; i < amount; i++) skipDay();
    }

    public void addObserverBank(Bank bank) {
        banks.add(bank);
    }

    private void skipDay() {
        dateTime = dateTime.plusDays(1);
        for (Bank bank : banks) {
            bank.notifyAccounts(dateTime);
        }
    }

    @Override
    public String toString() {
        return "TimeMachine{" +
                "dateTime=" + dateTime +
                ", id=" + id +
                '}';
    }
}
