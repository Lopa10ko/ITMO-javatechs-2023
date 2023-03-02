package ru.lopa10ko.banks.console;

import lombok.Getter;
import lombok.Setter;
import org.beryx.textio.TextIO;
import ru.lopa10ko.banks.Banks.Bank;
import ru.lopa10ko.banks.Banks.CentralBank;
import ru.lopa10ko.banks.Clients.BasicClient;
import ru.lopa10ko.banks.console.Handlers.CentralBankLogicHandler;
import ru.lopa10ko.banks.console.Handlers.MainHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MasterConsole {
    private static volatile MasterConsole masterConsoleInstance;
    @Getter
    private TextIO textIO;
    @Getter
    private final double daysInYear = LocalDate.now().lengthOfYear();
    @Getter
    private MainHandler mainHandler;
    @Getter
    private CentralBankLogicHandler centralBankLogicHandler;
    @Getter @Setter
    private CentralBank centralBank;
    @Getter @Setter
    private List<UUID> accounts;
    @Getter @Setter
    private List<BasicClient> clients;
    @Getter @Setter
    private List<Bank> banks;

    private MasterConsole(TextIO textIO) {
        this.textIO = textIO;
        accounts = new ArrayList<>();
        clients = new ArrayList<>();
        banks = new ArrayList<>();
        centralBankLogicHandler = new CentralBankLogicHandler(Options.getCentralBankLogicOptions());
        mainHandler = new MainHandler(centralBankLogicHandler, Options.getMainHandlerOptions());
        centralBankLogicHandler.setSuccessor(mainHandler);
    }

    public static MasterConsole getInstance(TextIO textIO) {
        MasterConsole localInstance = masterConsoleInstance;
        if (localInstance == null) {
            synchronized (MasterConsole.class) {
                localInstance = masterConsoleInstance;
                if (localInstance == null) {
                    masterConsoleInstance = localInstance = new MasterConsole(textIO);
                }
            }
        }
        return localInstance;
    }
}
