package ru.lopa10ko.cats.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CatOwnerDto {
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private UUID uuid;
    @Setter(AccessLevel.NONE)
    private String name;
    @Setter(AccessLevel.NONE)
    private LocalDate birthDay;
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    private List<CatDto> cats;

    public List<CatDto> getCats() {
        return Collections.unmodifiableList(cats);
    }
}
