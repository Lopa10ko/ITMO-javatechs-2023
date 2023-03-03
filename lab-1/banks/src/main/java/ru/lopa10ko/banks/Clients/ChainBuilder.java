package ru.lopa10ko.banks.Clients;

public interface ChainBuilder {
    /**
     * Builder method to access address to ClientBuilder
     * @param address raw string value, representing address of a client
     * @return ChainBuilder to continue building process
     */
    ChainBuilder withAddress(String address);

    /**
     * Builder method to access passport to ClientBuilder
     * @param passport raw string value, representing passport of a client
     * @return ChainBuilder to continue building process
     */
    ChainBuilder withPassport(String passport);

    /**
     *
     * @return BasicClient built entity
     */
    BasicClient build();
}
