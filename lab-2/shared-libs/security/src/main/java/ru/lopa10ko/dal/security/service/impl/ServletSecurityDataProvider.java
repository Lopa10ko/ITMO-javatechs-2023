package ru.lopa10ko.dal.security.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.lopa10ko.dal.security.model.CatsAuthentication;
import ru.lopa10ko.dal.security.service.SecurityDataProvider;

public class ServletSecurityDataProvider implements SecurityDataProvider {
    @Override
    public UUID getOwnerId() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(CatsAuthentication.class::cast)
            .map(CatsAuthentication::getUuid)
            .orElseThrow(RuntimeException::new);
    }
}
