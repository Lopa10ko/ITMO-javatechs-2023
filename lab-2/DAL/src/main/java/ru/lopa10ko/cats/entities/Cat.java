package ru.lopa10ko.cats.entities;

import jakarta.persistence.*;
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
    @ManyToMany(targetEntity = Cat.class, fetch = FetchType.EAGER)
    @JoinTable(name = "cat_cat",
            joinColumns = {@JoinColumn(name = "cat_uuid", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "catfriend_uuid", referencedColumnName = "uuid")})
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
        if (cat != null && !catFriends.contains(cat)) {
            catFriends.add(cat);
            cat.addFriend(this);
        }
    }

    public void removeFriend(Cat cat) {
        if (cat != null && catFriends.contains(cat)) {
            catFriends.remove(cat);
            cat.removeFriend(this);
        }
    }

    public List<Cat> getCatFriends() {
        return Collections.unmodifiableList(catFriends);
    }
}
