package ru.lopa10ko.banks.Banks;

import ru.lopa10ko.banks.Clients.ObserverObject;

public interface ObservableObject {
    void addObserverClient(ObserverObject client);
    void removeObserverClient(ObserverObject client);
    void notifyClients();
}
