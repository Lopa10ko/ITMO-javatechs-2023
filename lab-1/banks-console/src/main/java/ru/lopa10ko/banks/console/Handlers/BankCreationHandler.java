package ru.lopa10ko.banks.console.Handlers;

import org.beryx.textio.TextIO;
import ru.lopa10ko.banks.Banks.Bank;
import ru.lopa10ko.banks.Entities.BankInfo;
import ru.lopa10ko.banks.Entities.DepositRule;
import ru.lopa10ko.banks.Entities.DepositRules;
import ru.lopa10ko.banks.console.Main;
import ru.lopa10ko.banks.console.MasterConsole;
import ru.lopa10ko.banks.console.Options;

public class BankCreationHandler extends Handler {
    public BankCreationHandler(Handler successor) {
        this.successor = successor;
    }

    @Override
    void handleRequest(String request, MasterConsole master) {
        TextIO textIO = master.getTextIO();
        Main.clearScreen(textIO.getTextTerminal());
        textIO.getTextTerminal().printf("Creating Bank:\n");

        String bankName = textIO.newStringInputReader()
                .withDefaultValue("default")
                .read("Provide bank name: ");
        int commissionDay = textIO.newIntInputReader()
                .withDefaultValue(1)
                .read("Provide commission day: ");
        long freezeTime = textIO.newLongInputReader()
                .withDefaultValue(1L)
                .read("Provide freeze time for untrusted clients in days: ");
        double debitPercent = textIO.newDoubleInputReader()
                .withDefaultValue(0d)
                .read("Provide DEBIT percent: ") / master.getDaysInYear();
        double untrustedUserWithdrawLimit = textIO.newDoubleInputReader()
                .withDefaultValue(0d)
                .read("Provide withdraw limit for untrusted clients: ");
        double creditLimit = textIO.newDoubleInputReader()
                .withDefaultValue(0d)
                .read("Provide CREDIT limit: ");
        double creditCommission = textIO.newDoubleInputReader()
                .withDefaultValue(0d)
                .read("Provide CREDIT commission value: ");
        int rulesQuantity = textIO.newIntInputReader()
                .withDefaultValue(0)
                .read("\nBuilding DEPOSIT percent rules\nProvide quantity: ");
        DepositRules.DepositRulesBuilder depositRulesBuilder = DepositRules.getBuilder();
        for (int i = 0; i < rulesQuantity; i++) {
            double criticalValue = textIO.newDoubleInputReader()
                    .withDefaultValue(0d)
                    .read(String.format("DEPOSIT rule critical value #%d: ", i + 1));
            double percent = textIO.newDoubleInputReader()
                    .withDefaultValue(0d)
                    .read(String.format("DEPOSIT rule percent #%d: ", i + 1)) / master.getDaysInYear();
            depositRulesBuilder.addDepositRule(new DepositRule(criticalValue, percent));
        }

        DepositRules depositRules = depositRulesBuilder.build();
        var bankInfo = new BankInfo(
                commissionDay,
                freezeTime,
                debitPercent,
                untrustedUserWithdrawLimit,
                creditLimit,
                creditCommission,
                depositRules
        );
        Bank bank = new Bank(bankName, bankInfo);
        master.getBanks().add(bank);
        master.getTextIO().newStringInputReader()
                .withMinLength(0)
                .withPropertiesPrefix("custom.neutral")
                .read("Bank successfully created\n" + bank + Options.getConfirm());
        Main.clearScreen(master.getTextIO().getTextTerminal());

        successor.handleRequest(request, master);
    }
}
