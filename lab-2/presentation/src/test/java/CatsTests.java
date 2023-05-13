import lombok.Getter;
import org.junit.jupiter.api.Test;
import ru.lopa10ko.cats.commons.CatColor;
import ru.lopa10ko.cats.dao.CatOwnerRepository;
import ru.lopa10ko.cats.dao.CatRepository;
import ru.lopa10ko.cats.entities.Cat;
import ru.lopa10ko.cats.entities.CatOwner;
import ru.lopa10ko.cats.services.CatFacadeImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CatsTests {

    private final CatFacadeImpl catFacade;
    private final CatRepository catRepository;
    private final CatOwnerRepository catOwnerRepository;

    public CatsTests() {
        catFacade = mock(CatFacadeImpl.class);
        catRepository = mock(CatRepository.class);
        catOwnerRepository = mock(CatOwnerRepository.class);
    }

    @Getter
    private static class TestContext {
        private final List<CatOwner> catOwners;
        private final List<Cat> cats;

        public TestContext(int catOwnersQuantity, int catsQuantity) {
            this.catOwners = new ArrayList<>();
            this.cats = new ArrayList<>();
            IntStream.iterate(0, i -> i < catOwnersQuantity, i -> i + 1).mapToObj(i -> new CatOwner(String.format("Owner%d", i), LocalDate.now())).forEach(catOwners::add);
            IntStream.iterate(0, i -> i < catsQuantity, i -> i + 1).mapToObj(i -> new Cat(String.format("Cat%d", i), LocalDate.now(), String.format("Breed%d", i), CatColor.black)).forEach(cats::add);
        }
    }

    @Test
    public void addFriendsCatsToOwnerThenDeleteCat_AssertDeletionInEntitiesLists() {
        TestContext testContext = new TestContext(1, 2);
        CatOwner catOwner = testContext.getCatOwners().get(0);
        Cat cat0 = testContext.getCats().get(0), cat1 = testContext.getCats().get(1);
        UUID catOwnerUuid = UUID.randomUUID(), cat0Uuid = UUID.randomUUID(), cat1Uuid = UUID.randomUUID();
        when(catOwnerRepository.findById(catOwnerUuid)).thenReturn(Optional.ofNullable(catOwner));
        when(catRepository.findById(cat0Uuid)).thenReturn(Optional.ofNullable(cat0));
        when(catRepository.findById(cat1Uuid)).thenReturn(Optional.ofNullable(cat1));
        catFacade.addPet(catOwnerUuid, cat0Uuid);
        catFacade.addPet(catOwnerUuid, cat1Uuid);
        catFacade.addFriend(cat0Uuid, cat1Uuid);
        catFacade.deleteCat(cat0Uuid);
        assertTrue(cat1.getCatFriends().stream().noneMatch(f -> f.getUuid().equals(cat0Uuid)));
        assertTrue(catOwner.getCats().stream().noneMatch(f -> f.getUuid().equals(cat0Uuid)));
    }
}
