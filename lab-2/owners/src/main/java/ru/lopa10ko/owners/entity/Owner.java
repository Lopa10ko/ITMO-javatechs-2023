package ru.lopa10ko.owners.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Owner {
    @Id
    @EqualsAndHashCode.Include
    private UUID uuid;
    private String name;
    private LocalDate birthDay;
    @ElementCollection
    private Set<UUID> cats = new HashSet<>();
}
