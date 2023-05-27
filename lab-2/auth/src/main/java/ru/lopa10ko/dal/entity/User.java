package ru.lopa10ko.dal.entity;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.lopa10ko.dal.security.model.Roles;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "catsuser")
public class User {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private UUID uuid;
    private String username;
    private String password;
    private UUID catOwnerId;
    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    private Set<Roles> roles;
}
