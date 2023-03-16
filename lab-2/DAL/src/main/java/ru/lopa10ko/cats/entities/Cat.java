package ru.lopa10ko.cats.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.lopa10ko.cats.commons.CatColor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID uuid;
    private String name;
    private LocalDate birthDay;
    private String breed;
    @Enumerated(value = EnumType.STRING)
    private CatColor catColor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catowner_uuid")
    @ToString.Exclude
    private CatOwner catOwner;
    @ManyToMany(targetEntity = Cat.class)
    @JoinTable(name = "cat_cat", joinColumns = {@JoinColumn(name = "cat_uuid")}, inverseJoinColumns = {@JoinColumn(name = "catfriends_uuid")})
    @ToString.Exclude
    private List<Cat> catFriends;

    public Cat(String name, LocalDate birthDay, String breed, CatColor catColor) {
        this.name = name;
        this.birthDay = birthDay;
        this.breed = breed;
        this.catColor = catColor;
        catFriends = new ArrayList<>();
    }

    public void addFriend(Cat cat) {
        catFriends.add(cat);
        cat.setCatFriends(Stream.concat(getCatFriends().stream(), Stream.of(this)).collect(Collectors.toList()));
    }
    public List<Cat> getCatFriends() {
        return Collections.unmodifiableList(catFriends);
    }
}
