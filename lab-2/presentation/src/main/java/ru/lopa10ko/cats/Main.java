package ru.lopa10ko.cats;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import ru.lopa10ko.cats.commons.CatColor;
import ru.lopa10ko.cats.dao.CatOwnerRepositoryImpl;
import ru.lopa10ko.cats.dao.CatRepositoryImpl;
import ru.lopa10ko.cats.dto.CatDto;
import ru.lopa10ko.cats.dto.CatOwnerDto;
import ru.lopa10ko.cats.services.CatFacade;
import ru.lopa10ko.cats.services.CatFacadeImpl;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("cat").createEntityManager();
        CatFacade catFacade = new CatFacadeImpl(new CatRepositoryImpl(entityManager),
                new CatOwnerRepositoryImpl(entityManager),
                entityManager);
        CatOwnerDto owner1 = catFacade.createCatOwner("Owner" + System.currentTimeMillis(), LocalDate.now());
        CatDto cat1 = catFacade.createCat("Cat" + System.currentTimeMillis(), LocalDate.now(), "Test", CatColor.black);
        CatDto cat2 = catFacade.createCat("Cat" + System.currentTimeMillis(), LocalDate.now(), "Test", CatColor.red);
        CatDto cat3 = catFacade.createCat("Cat" + System.currentTimeMillis(), LocalDate.now(), "Test", CatColor.red);
        System.out.println(catFacade.readCat(cat2.getUuid()));
        System.out.println(catFacade.readCat(cat1.getUuid()));
        catFacade.addFriend(cat1.getUuid(), cat2.getUuid());
        catFacade.addFriend(cat2.getUuid(), cat3.getUuid());
        catFacade.addPet(owner1.getUuid(), cat1.getUuid());
    }
}