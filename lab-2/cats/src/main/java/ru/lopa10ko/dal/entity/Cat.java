package ru.lopa10ko.dal.entity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.lopa10ko.dal.commons.CatColor;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Cat {
    @Id
    @EqualsAndHashCode.Include
    private UUID uuid;
    private String name;
    private LocalDate birthDay;
    private String breed;
    private UUID ownerId;
    @Enumerated(EnumType.STRING)
    private CatColor catColor;
    @ManyToMany(targetEntity = Cat.class, fetch = FetchType.EAGER)
    @JoinTable(name = "cat_cat",
        joinColumns = {@JoinColumn(name = "cat_uuid", referencedColumnName = "uuid")},
        inverseJoinColumns = {@JoinColumn(name = "catfriend_uuid", referencedColumnName = "uuid")})
    @ToString.Exclude
    private List<Cat> catFriends;

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

