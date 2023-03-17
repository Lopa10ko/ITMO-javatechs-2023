import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.lopa10ko.cats.commons.CatColor;
import ru.lopa10ko.cats.controllers.CatController;
import ru.lopa10ko.cats.controllers.CatControllerImpl;
import ru.lopa10ko.cats.controllers.CatOwnerController;
import ru.lopa10ko.cats.controllers.CatOwnerControllerImpl;
import ru.lopa10ko.cats.dao.CatRepositoryImpl;
import ru.lopa10ko.cats.entities.Cat;
import ru.lopa10ko.cats.entities.CatOwner;
import ru.lopa10ko.cats.extensions.CatExtension;
import ru.lopa10ko.cats.extensions.CatOwnerExtension;
import ru.lopa10ko.cats.services.CatFacade;
import ru.lopa10ko.cats.services.CatFacadeImpl;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CatsTests {

    private final CatFacade catFacade;
    private final CatController catController;
    private final CatOwnerController catOwnerController;
    public CatsTests() {
        catFacade = mock(CatFacadeImpl.class);
        catController = new CatControllerImpl(catFacade);
        catOwnerController = new CatOwnerControllerImpl(catFacade);
    }
    @Test
    void readInitializedCat_AssertedServiceControllerCompatibility() {
        UUID uuid = UUID.randomUUID();
        Cat catAssert = new Cat("Test", LocalDate.now(), "Test", CatColor.red);
        catAssert.setUuid(uuid);
        when(catFacade.readCat(uuid)).thenReturn(CatExtension.asDto(catAssert));
        assertEquals(catController.readCat(uuid), CatExtension.asDto(catAssert));
    }
}
