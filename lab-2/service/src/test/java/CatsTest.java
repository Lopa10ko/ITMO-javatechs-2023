import org.junit.jupiter.api.Test;
import ru.lopa10ko.cats.commons.CatColor;
import ru.lopa10ko.cats.entities.Cat;
import ru.lopa10ko.cats.entities.CatOwner;
import ru.lopa10ko.cats.extensions.CatExtension;
import ru.lopa10ko.cats.extensions.CatOwnerExtension;
import ru.lopa10ko.cats.services.CatFacade;
import ru.lopa10ko.cats.services.CatFacadeImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CatsTest {
    private final CatFacade catFacade;
    public CatsTest() {
        catFacade = mock(CatFacadeImpl.class);
    }
    @Test
    public void persistEntity_EntityPersistenceRestored() {
        CatOwner owner = new CatOwner("Owner" + System.currentTimeMillis(), LocalDate.now());
        Cat cat1 = new Cat("Cat" + System.currentTimeMillis(), LocalDate.now(), "Test", CatColor.black);
        when(catFacade.readCat(cat1.getUuid())).thenReturn(CatExtension.asDto(cat1));
        when(catFacade.readCatOwner(owner.getUuid())).thenReturn(CatOwnerExtension.asDto(owner));
        assertEquals(catFacade.readCat(cat1.getUuid()), CatExtension.asDto(cat1));
        assertEquals(catFacade.readCatOwner(owner.getUuid()), CatOwnerExtension.asDto(owner));
    }
}
