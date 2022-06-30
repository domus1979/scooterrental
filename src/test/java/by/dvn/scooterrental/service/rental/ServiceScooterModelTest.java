package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.rental.DtoScooterModel;
import by.dvn.scooterrental.handlerexception.HandleBadCondition;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.handlerexception.HandleNotModified;
import by.dvn.scooterrental.model.rental.ScooterModel;
import by.dvn.scooterrental.repository.rental.MySqlRepoScooterModel;
import by.dvn.scooterrental.service.AbstractService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceScooterModelTest {
    @Mock
    private MySqlRepoScooterModel repo;
    @Mock
    private ModelMapper modelMapper = new ModelMapper();

    @Spy
    private AbstractService serviceObj = new ServiceScooterModel(repo, modelMapper);

    private static List<ScooterModel> objs;
    private static List<DtoScooterModel> dtoObjs;

    @BeforeAll
    public static void initData() {
        objs = new ArrayList<>();
        dtoObjs = new ArrayList<>();

        objs.add(new ScooterModel(1, "m1", 1, 1, 1));
        objs.add(new ScooterModel(2, "m1", 1, 1, 1));
        dtoObjs.add(new DtoScooterModel(1, "m1", 1, 1, 1));
        dtoObjs.add(new DtoScooterModel(2, "m1", 1, 1, 1));

    }

    @AfterAll
    public static void clearTestRoles() {
        objs.clear();
        dtoObjs.clear();
    }

    @Test
    void create() throws HandleNotModified, HandleBadRequestPath, HandleBadCondition, HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();

        when(repo.create(any(ScooterModel.class))).thenReturn(true);

        Assertions.assertTrue(serviceObj.create(objs.get(0)));
        Assertions.assertNotNull(serviceObj.create(objs.get(0)));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.create(null));
    }

    @Test
    void update() throws HandleNotModified, HandleBadCondition {
        doReturn(repo).when(serviceObj).getMySqlRepo();

        objs.get(0).setName("MMM");
        ScooterModel objToFalse = new ScooterModel(4, "jhk", 1, 1, 1);

        when(repo.update(objs.get(0))).thenReturn(true);
        when(repo.read(1)).thenReturn(objs.get(0));

        Assertions.assertTrue(serviceObj.update(objs.get(0)));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.update(objToFalse));
        objs.get(0).setName("m1");
    }

    @Test
    void delete() throws HandleBadRequestPath, HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();

        when(repo.delete(1)).thenReturn(true);

        Assertions.assertTrue(serviceObj.delete(objs.get(0).getId()));
        Assertions.assertThrowsExactly(HandleNotFoundExeption.class, () -> serviceObj.delete(4));
    }

    @Test
    void read() throws HandleBadRequestPath, HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(modelMapper).when(serviceObj).getModelMapper();

        when(modelMapper.map(objs.get(0), DtoScooterModel.class)).thenReturn(dtoObjs.get(0));
        when(repo.read(1)).thenReturn(objs.get(0));

        Assertions.assertNotNull(serviceObj.read(1));
        Assertions.assertEquals(dtoObjs.get(0), serviceObj.read(1));
        Assertions.assertThrows(HandleNotFoundExeption.class, () -> serviceObj.read(5));
    }

    @Test
    void readAll() throws HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(modelMapper).when(serviceObj).getModelMapper();

        for (int i = 0; i < objs.size(); i++) {
            when(modelMapper.map(objs.get(i), DtoScooterModel.class)).thenReturn(dtoObjs.get(i));
        }

        when(repo.readAll()).thenReturn(objs);

        Assertions.assertNotNull(serviceObj.readAll());
        Assertions.assertEquals(dtoObjs, serviceObj.readAll());
    }

    @Test
    void checkObject() throws HandleBadCondition {
        Assertions.assertTrue(serviceObj.checkObject(objs.get(0), false));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.checkObject(null, false));
    }

}