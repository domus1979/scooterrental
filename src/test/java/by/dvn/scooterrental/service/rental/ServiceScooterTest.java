package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.rental.DtoRentalPoint;
import by.dvn.scooterrental.dto.rental.DtoScooter;
import by.dvn.scooterrental.dto.rental.DtoScooterModel;
import by.dvn.scooterrental.handlerexception.HandleBadCondition;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.handlerexception.HandleNotModified;
import by.dvn.scooterrental.model.rental.RentalPoint;
import by.dvn.scooterrental.model.rental.Scooter;
import by.dvn.scooterrental.model.rental.ScooterModel;
import by.dvn.scooterrental.repository.rental.MySqlRepoRentalPoint;
import by.dvn.scooterrental.repository.rental.MySqlRepoScooter;
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
class ServiceScooterTest {
    @Mock
    private MySqlRepoScooter repo;
    @Mock
    private ModelMapper modelMapper = new ModelMapper();
    @Mock
    private MySqlRepoScooterModel repoScooterModel;
    @Mock
    private MySqlRepoRentalPoint repoRentalPoint;

    @Spy
    private ServiceScooterModel serviceScooterModel = new ServiceScooterModel(repoScooterModel, modelMapper);
    @Spy
    private ServiceRentalPoint serviceRentalPoint = new ServiceRentalPoint(repoRentalPoint, modelMapper);
    @Spy
    private AbstractService serviceObj = new ServiceScooter(repo, modelMapper);

    private static List<Scooter> objs;
    private static List<DtoScooter> dtoObjs;
    private static ScooterModel sm;
    private static RentalPoint rp;
    private static DtoScooterModel dtosm;
    private static DtoRentalPoint dtorp;

    @BeforeAll
    public static void initData() {
        objs = new ArrayList<>();
        dtoObjs = new ArrayList<>();

        rp = new RentalPoint(1, "Belarus", null);
        sm = new ScooterModel(1, "mod1", 1, 1, 1);

        objs.add(new Scooter(1, "sc1", sm, 2021, rp));
        objs.add(new Scooter(2, "sc2", sm, 2022, rp));
        dtoObjs.add(new DtoScooter(1, "sc1", dtosm, 2021, dtorp));
        dtoObjs.add(new DtoScooter(2, "sc2", dtosm, 2022, dtorp));
    }

    @AfterAll
    public static void clearTestRoles() {
        objs.clear();
        dtoObjs.clear();
    }

    @Test
    void create() throws HandleNotModified, HandleBadRequestPath, HandleBadCondition, HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(serviceScooterModel).when(((ServiceScooter) serviceObj)).getServiceScooterModel();
        doReturn(serviceRentalPoint).when(((ServiceScooter) serviceObj)).getServiceRentalPoint();
        doReturn(repoScooterModel).when(serviceScooterModel).getMySqlRepo();
        doReturn(repoRentalPoint).when(serviceRentalPoint).getMySqlRepo();

        when(repo.create(any(Scooter.class))).thenReturn(true);
        when(repoScooterModel.read(any(Integer.class))).thenReturn(sm);
        when(repoRentalPoint.read(any(Integer.class))).thenReturn(rp);

        Assertions.assertTrue(serviceObj.create(objs.get(0)));
        Assertions.assertNotNull(serviceObj.create(objs.get(0)));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.create(null));
    }

    @Test
    void update() throws HandleNotModified, HandleBadCondition {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(serviceScooterModel).when(((ServiceScooter) serviceObj)).getServiceScooterModel();
        doReturn(serviceRentalPoint).when(((ServiceScooter) serviceObj)).getServiceRentalPoint();
        doReturn(repoScooterModel).when(serviceScooterModel).getMySqlRepo();
        doReturn(repoRentalPoint).when(serviceRentalPoint).getMySqlRepo();

        objs.get(0).setName("Scooter");

        when(repo.read(any(Integer.class))).thenReturn(objs.get(0));
        when(repo.update(any(Scooter.class))).thenReturn(true);
        when(repoRentalPoint.read(any(Integer.class))).thenReturn(rp);
        when(repoScooterModel.read(any(Integer.class))).thenReturn(sm);

        Assertions.assertTrue(serviceObj.update(objs.get(0)));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.update(null));
        objs.get(0).setName("sc1");
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

        when(modelMapper.map(objs.get(0), DtoScooter.class)).thenReturn(dtoObjs.get(0));
        when(repo.read(1)).thenReturn(objs.get(0));

        Assertions.assertNotNull(serviceObj.read(1));
        Assertions.assertEquals(dtoObjs.get(0), serviceObj.read(1));
    }

    @Test
    void readAll() throws HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(modelMapper).when(serviceObj).getModelMapper();

        for (int i = 0; i < objs.size(); i++) {
            when(modelMapper.map(objs.get(i), DtoScooter.class)).thenReturn(dtoObjs.get(i));
        }

        when(repo.readAll()).thenReturn(objs);

        Assertions.assertNotNull(serviceObj.readAll());
        Assertions.assertEquals(dtoObjs, serviceObj.readAll());
    }

    @Test
    void checkObject() throws HandleBadCondition {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(serviceScooterModel).when(((ServiceScooter) serviceObj)).getServiceScooterModel();
        doReturn(serviceRentalPoint).when(((ServiceScooter) serviceObj)).getServiceRentalPoint();
        doReturn(repoScooterModel).when(serviceScooterModel).getMySqlRepo();
        doReturn(repoRentalPoint).when(serviceRentalPoint).getMySqlRepo();

        when(repoRentalPoint.read(any(Integer.class))).thenReturn(rp);
        when(repoScooterModel.read(any(Integer.class))).thenReturn(sm);

        Assertions.assertTrue(serviceObj.checkObject(objs.get(0), false));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.checkObject(null, false));
    }
}