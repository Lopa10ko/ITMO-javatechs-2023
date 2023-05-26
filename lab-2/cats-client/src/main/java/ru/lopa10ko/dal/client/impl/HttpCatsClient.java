package ru.lopa10ko.dal.client.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import ru.lopa10ko.dal.client.CatsClient;
import ru.lopa10ko.dal.client.dto.CatDto;
import ru.lopa10ko.dal.commons.CatColor;

@Slf4j
@RequiredArgsConstructor
public class HttpCatsClient implements CatsClient {

    private final WebClient catsWebClient;

    @Override
    public CatDto getById(UUID uuid) {
        log.info("Making request to /api/cats/{}", uuid);

        HttpServletRequest request = getRequest();

        return catsWebClient
            .get()
            .uri("/cats/%s".formatted(uuid))
            .header("Authorization", request.getHeader("Authorization"))
            .retrieve()
            .bodyToMono(CatDto.class)
            .block();
    }

    @Override
    public CatDto adminGetById(UUID uuid) {
        log.info("Making request to /api/admin/cats/{}", uuid);

        HttpServletRequest request = getRequest();

        return catsWebClient
            .get()
            .uri("/admin/cats/%s".formatted(uuid))
            .header("Authorization", request.getHeader("Authorization"))
            .retrieve()
            .bodyToMono(CatDto.class)
            .block();
    }

    private static HttpServletRequest getRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
            .filter(ServletRequestAttributes.class::isInstance)
            .map(ServletRequestAttributes.class::cast)
            .map(ServletRequestAttributes::getRequest)
            .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<CatDto> getByParams(List<String> name, List<UUID> uuid, List<LocalDate> birthDay, List<CatColor> color, List<String> breed) {
        log.info(
            "Making request to /api/cats?name={}&uuid={}&birthDay={}&color={}&breed={}",
            name,
            uuid,
            birthDay,
            color,
            breed
            );

        HttpServletRequest request = getRequest();

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("name", name);
        params.put("uuid", uuid.stream().map(UUID::toString).toList());
        params.put("birthDay", birthDay.stream().map(LocalDate::toString).toList());
        params.put("color", color.stream().map(CatColor::toString).toList());
        params.put("breed", breed);

        return catsWebClient
            .get()
            .uri(it -> it.path("/cats").queryParams(params).build())
            .header("Authorization", request.getHeader("Authorization"))
            .retrieve()
            .bodyToFlux(CatDto.class)
            .collectList()
            .block();
    }

    @Override
    public List<CatDto> adminGetByParams(List<String> name, List<UUID> uuid, List<LocalDate> birthDay,
                                         List<CatColor> color, List<String> breed) {
        log.info(
            "Making request to /api/admin/cats?name={}&uuid={}&birthDay={}&color={}&breed={}",
            name,
            uuid,
            birthDay,
            color,
            breed
        );

        HttpServletRequest request = getRequest();

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("name", name);
        params.put("uuid", uuid.stream().map(UUID::toString).toList());
        params.put("birthDay", birthDay.stream().map(LocalDate::toString).toList());
        params.put("color", color.stream().map(CatColor::toString).toList());
        params.put("breed", breed);

        return catsWebClient
            .get()
            .uri(it -> it.path("/admin/cats").queryParams(params).build())
            .header("Authorization", request.getHeader("Authorization"))
            .retrieve()
            .bodyToFlux(CatDto.class)
            .collectList()
            .block();
    }
}
