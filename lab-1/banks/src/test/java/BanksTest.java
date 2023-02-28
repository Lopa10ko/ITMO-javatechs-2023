import org.junit.jupiter.api.Test;
import ru.lopa10ko.banks.Accounts.CreditAccount;
import ru.lopa10ko.banks.Accounts.DebitAccount;
import ru.lopa10ko.banks.Accounts.DepositAccount;
import ru.lopa10ko.banks.Banks.Bank;
import ru.lopa10ko.banks.Banks.CentralBank;
import ru.lopa10ko.banks.Clients.Client;
import ru.lopa10ko.banks.Entities.BankInfo;
import ru.lopa10ko.banks.Entities.DepositRule;
import ru.lopa10ko.banks.Entities.DepositRules;
import ru.lopa10ko.banks.Tools.ValueAmountException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BanksTest {
    private DepositRules defaultDepositRules;
    private List<Client> testClients;
    private final double daysInYear = LocalDate.now().lengthOfYear();

    public BanksTest() {
        Client client1 = (Client) Client.getBuilder()
                .withName("Losha")
                .withLastName("Gopatenko")
                .build();
        client1.setAddress("Kronverskii, 49");
        client1.setPassport("2282281337");
        Client client2 = (Client) Client.getBuilder()
                .withName("Gosha")
                .withLastName("Lopatenko")
                .withAddress("Lomonosova, 7")
                .withPassport("4017123654")
                .build();
        Client client3 = (Client) Client.getBuilder()
                .withName("Gosha")
                .withLastName("Kruglov")
                .build();
        client3.setAddress("Earth");

        testClients = List.of(client1, client2, client3);
        defaultDepositRules = DepositRules.getBuilder()
                .addDepositRule(new DepositRule(20000f, 3f / daysInYear))
                .addDepositRule(new DepositRule(50000f, 5f / daysInYear))
                .addDepositRule(new DepositRule(100000f, 8f / daysInYear))
                .addDepositRule(new DepositRule(100000.01, 10f / daysInYear))
                .build();
    }

    @Test
    public void PerformTransactionWithVerificationCheck_CatchUnsuccessfulTransaction() {
        var centralBank = CentralBank.getInstance();
        Bank spermBank1 = centralBank.createBank("Sperm", new BankInfo(30, 10, 8.5f / daysInYear, 10000f, 50000f, 1000f, defaultDepositRules));
        Bank spermBank2 = centralBank.createBank("Sperm", new BankInfo(30, 10, 8.5f / daysInYear, 10000f, 50000f, 1000f, defaultDepositRules));
        UUID clientVerifiedDepositId = spermBank1.createAccount(DebitAccount.class, testClients.get(0), 50000f);
        assertTrue(testClients.get(0).getIsVerified());
        UUID clientUnverifiedDebitId = spermBank2.createAccount(DebitAccount.class, testClients.get(2), 1000000f);
        assertFalse(testClients.get(2).getIsVerified());
        assertThrows(ValueAmountException.class, () -> centralBank.createTransaction(clientVerifiedDepositId, clientUnverifiedDebitId, 50001f));
    }

    @Test
    public void AggregateTimeManagerCheckCommission_CommissionAccrued() {
        var centralBank = CentralBank.getInstance();
        Bank spermBank = centralBank.createBank("Sperm", new BankInfo(30, 10, 8.5f / daysInYear, 10000f, 50000f, 1000f, defaultDepositRules));
        UUID depositId = spermBank.createAccount(DepositAccount.class, testClients.get(0), 50000f);
        UUID debitId = spermBank.createAccount(DebitAccount.class, testClients.get(0), 1000000f);
        UUID creditId = spermBank.createAccount(CreditAccount.class, testClients.get(0), -1f);
        String moneyBankBefore = Objects.requireNonNull(spermBank.getAccounts().stream().filter(account -> account.getId().equals(depositId)).findFirst().orElse(null)).toString();
        centralBank.getTimeMachine().skipDays(31 + spermBank.getBankInfo().getCommissionDay());
        String moneyBankAfter = Objects.requireNonNull(spermBank.getAccounts().stream().filter(account -> account.getId().equals(depositId)).findFirst().orElse(null)).toString();
        assertNotEquals(moneyBankAfter, moneyBankBefore);
    }

    @Test
    public void PerformOneSidedTransaction_CheckSuccessfulTransaction() {
        var centralBank = CentralBank.getInstance();
        Bank spermBank = centralBank.createBank("Sperm", new BankInfo(30, 10, 8.5f / daysInYear, 10000f, 50000f, 1000f, defaultDepositRules));
        UUID creditId = spermBank.createAccount(CreditAccount.class, testClients.get(0), 500f);
        String moneyBankBefore = Objects.requireNonNull(spermBank.getAccounts().stream().filter(account -> account.getId().equals(creditId)).findFirst().orElse(null)).toString();
        spermBank.createWithdrawTransaction(creditId, 5000f);
        String moneyBankAfter = Objects.requireNonNull(spermBank.getAccounts().stream().filter(account -> account.getId().equals(creditId)).findFirst().orElse(null)).toString();
        assertNotEquals(moneyBankBefore, moneyBankAfter);
        UUID debitId = spermBank.createAccount(DebitAccount.class, testClients.get(2), 100f);
        assertThrows(ValueAmountException.class, () -> spermBank.createWithdrawTransaction(debitId, 101f));
    }
}
