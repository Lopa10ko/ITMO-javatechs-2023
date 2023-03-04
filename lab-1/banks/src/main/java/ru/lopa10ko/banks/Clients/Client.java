package ru.lopa10ko.banks.Clients;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.lopa10ko.banks.Accounts.Account;
import ru.lopa10ko.banks.Banks.Bank;
import ru.lopa10ko.banks.Tools.ClientBuilderException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
public class Client implements BasicClient, ObserverObject {
    @Setter(AccessLevel.NONE)
    private boolean isVerified = false;
    private final UUID id;
    private String name;
    private String lastName;
    private String address = "";
    private String passport = "";
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final List<String> clientHistory;
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final List<Account> accounts;
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final List<Account> accountObservable;
    @Setter(AccessLevel.NONE)
    private NameBuilder builder;

    private Client(String name, String lastName, String address, String passport) {
        this.id = UUID.randomUUID();
        accountObservable = new ArrayList<>();
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.passport = passport;
        this.isVerified = getIsVerified();
        clientHistory = new ArrayList<>();
        accounts = new ArrayList<>();
    }

    @Override
    public void addAccount(Account account) {
        if (!getIsVerified())
            accountObservable.add(account);
        accounts.add(account);
    }

    @Override
    public void subscribeToBank(Bank bank) {
        bank.addObserverClient(this);
    }

    @Override
    public void unsubscribeFromBank(Bank bank) {
        bank.removeObserverClient(this);
    }

    public boolean getIsVerified() {
        return !(getPassport().isBlank() || getAddress().isBlank());
    }

    public static NameBuilder getBuilder() {
        return new ClientBuilder();
    }

    public ArrayList<String> getClientHistory() {
        return new ArrayList<>(clientHistory);
    }

    public void setAddress(String address) {
        this.address = address;
        if (getIsVerified())
            doNotify();
    }

    public void setPassport(String passport) {
        this.passport = passport;
        if (getIsVerified())
            doNotify();
    }

    private void doNotify() {
        if (accountObservable.size() == 0)
            return;
        for (Account account: accountObservable) {
            account.removeWithdrawLimit();
        }
    }

    @Override
    public void update(String logMessage) {
        clientHistory.add(logMessage);
    }

    public static class ClientBuilder implements NameBuilder, LastNameBuilder, ChainBuilder {
        private String name;
        private String lastName;
        private String address;
        private String passport;

        public ClientBuilder() {
            this.name = "";
            this.lastName = "";
            this.address = "";
            this.passport = "";
        }

        @Override
        public ChainBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        @Override
        public ChainBuilder withPassport(String passport) {
            this.passport = passport;
            return this;
        }

        @Override
        public BasicClient build() throws ClientBuilderException {
            if (name.isBlank() || lastName.isBlank())
                throw ClientBuilderException.NoObligatoryProperties(name, lastName);
            return new Client(name, lastName, address, passport);
        }

        @Override
        public ChainBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        @Override
        public LastNameBuilder withName(String name) {
            this.name = name;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id.equals(client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", isVerified=" + isVerified +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", passport='" + passport + '\'' +
                '}';
    }
}
