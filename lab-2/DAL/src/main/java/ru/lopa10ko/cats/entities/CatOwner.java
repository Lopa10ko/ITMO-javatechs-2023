package ru.lopa10ko.cats.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class CatOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private UUID uuid;
    private String name;
    private LocalDate birthDay;
    @OneToMany(mappedBy = "catOwner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Cat> cats;

    public CatOwner(String name, LocalDate birthDay) {
        this.name = name;
        this.birthDay = birthDay;
        cats = new ArrayList<>();
    }

    public List<Cat> getCats() {
        return Collections.unmodifiableList(cats);
    }

    public void addCat(Cat cat) {
        cats.add(cat);
        cat.setCatOwner(this);
    }
}