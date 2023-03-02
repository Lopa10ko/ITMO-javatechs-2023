package ru.lopa10ko.banks.console.Handlers;

import org.beryx.textio.TextIO;
import ru.lopa10ko.banks.console.Main;
import ru.lopa10ko.banks.console.MasterConsole;

public class TimeWizardHandler extends Handler {
    public TimeWizardHandler(Handler successor) {
        this.successor = successor;
    }
    @Override
    void handleRequest(String request, MasterConsole master) {
        TextIO textIO = master.getTextIO();
        Main.clearScreen(textIO.getTextTerminal());
        textIO.getTextTerminal().printf("Skipping days in TIME MACHINE:\n");
        int daysToSkip = textIO.newIntInputReader()
                .withDefaultValue(0)
                .read("Provide quantity of days to skip: ");
        master.getCentralBank().getTimeMachine().skipDays(daysToSkip);
        Main.clearScreen(textIO.getTextTerminal());
        successor.handleRequest(request, master);
    }
}
