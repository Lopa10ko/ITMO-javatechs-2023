package ru.lopa10ko.cats.entities;

import jakarta.persistence.*;
import lombok.*;
import ru.lopa10ko.cats.commons.Role;

import java.util.Set;
import java.util.UUID;
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
    @OneToOne
    private CatOwner catOwner;
    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;
}
