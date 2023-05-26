package ru.lopa10ko.dal.services;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.lopa10ko.dal.security.service.SecurityDataProvider;
import ru.lopa10ko.owners.client.OwnersClient;
import ru.lopa10ko.owners.client.dto.OwnerDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuperOwnerServiceImpl implements SuperOwnerService{

    private final SecurityDataProvider securityDataProvider;
    private final OwnersClient ownersClient;

    @Override
    public OwnerDto getSelf() {
        return ownersClient.getSelf();
    }

    @Override
    public OwnerDto adminGetById(UUID uuid) {
        return ownersClient.adminGetById(uuid);
    }
}
