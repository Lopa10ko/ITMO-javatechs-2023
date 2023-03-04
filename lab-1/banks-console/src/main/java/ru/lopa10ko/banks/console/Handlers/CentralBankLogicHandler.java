package ru.lopa10ko.banks.console.Handlers;

import ru.lopa10ko.banks.console.MasterConsole;
import ru.lopa10ko.banks.console.Options;
import java.util.List;

public class CentralBankLogicHandler extends Handler {
    private final Handler successorBankCreation;
    private final Handler successorClientCreation;
    private final Handler successorAccountCreation;
    private final Handler successorTimeWizard;
    private final Handler successorTransaction;

    public CentralBankLogicHandler(List<String> options) {
        this.options = options;
        successorBankCreation = new BankCreationHandler(this);
        successorClientCreation = new ClientCreationHandler(this);
        successorAccountCreation = new AccountCreationHandler(this);
        successorTimeWizard = new TimeWizardHandler(this);
        successorTransaction = new TransactionHandler(this);
    }

    @Override
    void handleRequest(String request, MasterConsole master) {
        request = master.getTextIO().newStringInputReader()
                .withDefaultValue(Options.getExit())
                .withNumberedPossibleValues(Options.getCentralBankLogicOptions())
                .read(Options.getTitle());
        if (request.equals(Options.getExit()))
            successor.handleRequest(request, master);
        if (request.equals(Options.getCreateBank()))
            successorBankCreation.handleRequest(request, master);
        if (request.equals(Options.getCreateClient()))
            successorClientCreation.handleRequest(request, master);
        if (request.equals(Options.getCreateAccount()))
            successorAccountCreation.handleRequest(request, master);
        if (request.equals(Options.getSkipDays()))
            successorTimeWizard.handleRequest(request, master);
        if (request.equals(Options.getCreateTransaction()))
            successorTransaction.handleRequest(request, master);
    }
}
