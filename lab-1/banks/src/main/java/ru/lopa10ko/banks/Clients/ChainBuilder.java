package ru.lopa10ko.banks.Clients;

public interface ChainBuilder {
    ChainBuilder withAddress(String address);
    ChainBuilder withPassport(String passport);
    BasicClient build();
}
