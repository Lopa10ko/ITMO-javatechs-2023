package ru.lopa10ko.owners.client.impl;

import java.util.Optional;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import ru.lopa10ko.owners.client.OwnersClient;
import ru.lopa10ko.owners.client.dto.OwnerDto;

@RequiredArgsConstructor
public class HttpOwnersClient implements OwnersClient {

    private final WebClient ownersWebClient;

    @Override
    public OwnerDto getSelf() {
        var request = getRequest();

        return ownersWebClient
            .get()
            .uri("/cat-owners/self")
            .header("Authorization", request.getHeader("Authorization"))
            .retrieve()
            .bodyToMono(OwnerDto.class)
            .block();
    }

    @Override
    public OwnerDto adminGetById(UUID uuid) {
        var request = getRequest();

        return ownersWebClient
            .get()
            .uri("/admin/cat-owners/%s".formatted(uuid))
            .header("Authorization", request.getHeader("Authorization"))
            .retrieve()
            .bodyToMono(OwnerDto.class)
            .block();
    }

    private static HttpServletRequest getRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
            .filter(ServletRequestAttributes.class::isInstance)
            .map(ServletRequestAttributes.class::cast)
            .map(ServletRequestAttributes::getRequest)
            .orElseThrow(RuntimeException::new);
    }
}
