package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.account.DtoUser;
import by.dvn.scooterrental.dto.rental.*;
import by.dvn.scooterrental.handlerexception.HandleBadCondition;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.handlerexception.HandleNotModified;
import by.dvn.scooterrental.model.account.User;
import by.dvn.scooterrental.model.rental.*;
import by.dvn.scooterrental.model.rental.Order;
import by.dvn.scooterrental.repository.account.MySqlRepoUser;
import by.dvn.scooterrental.repository.rental.*;
import by.dvn.scooterrental.service.AbstractService;
import by.dvn.scooterrental.service.account.ServiceUser;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceOrderTest {
    @Mock
    private MySqlRepoOrder repo;
    @Mock
    private ModelMapper modelMapper = new ModelMapper();
    @Mock
    private MySqlRepoRentalPoint repoRentalPoint;
    @Mock
    private MySqlRepoScooter repoScooter;
    @Mock
    private MySqlRepoScooterModel repoScooterModel;
    @Mock
    private MySqlRepoUser repoUser;
    @Mock
    private MySqlRepoPriceList repoPriceList;

    @Mock
    private ServiceRentalPoint serviceRentalPoint = new ServiceRentalPoint(repoRentalPoint, modelMapper);


    @Spy
    private ServiceScooter serviceScooter = new ServiceScooter(repoScooter, modelMapper);

    @Mock
    private ServiceScooterModel serviceScooterModel = new ServiceScooterModel(repoScooterModel, modelMapper);
    @Mock
    private ServiceUser serviceUser = new ServiceUser(repoUser, modelMapper);
    @Mock
    private ServicePriceList servicePriceList = new ServicePriceList(repoPriceList, modelMapper);
    @Spy
    private AbstractService serviceObj = new ServiceOrder(repo, modelMapper);

    private static List<Order> objs;
    private static List<DtoOrder> dtoObjs;
    private static RentalPoint rp;
    private static RentalPoint rp1;
    private static Scooter sc;
    private static User us;
    private static PriceList pl;

    private static DtoRentalPoint dtorp;
    private static DtoRentalPoint dtorp1;
    private static DtoScooter dtosc;
    private static DtoUser dtous;
    private static DtoPriceList dtopl;

    @BeforeAll
    public static void initData() {
        objs = new ArrayList<>();
        dtoObjs = new ArrayList<>();

        ScooterModel sm = new ScooterModel(1, "sm", 1, 1, 1);
        rp = new RentalPoint(1, "Belarus", null);
        rp1 = new RentalPoint(2, "Poland", null);
        sc = new Scooter(1,
                "sc1",
                sm,
                2019,
                rp);
        us = new User(1, "user1", "1", "2", "3", "4", 22, new ArrayList<>());
        pl = new PriceList(1,
                sm,
                new PriceType(1, "pt1", new BigInteger("30"),false),
                new BigDecimal(100));
        DtoScooterModel dtosm = new DtoScooterModel(1, "sm", 1, 1, 1);
        dtorp = new DtoRentalPoint(1, "Belarus", null);
        dtorp1 = new DtoRentalPoint(2, "Poland", null);
        dtosc = new DtoScooter(1,
                "sc1",
                dtosm,
                2019,
                dtorp);
        dtous = new DtoUser(1, "user1", "2", "3", "4", 22, new ArrayList<>());
        dtopl = new DtoPriceList(1,
                dtosm,
                new DtoPriceType(1, "pt1", new BigInteger("30"),false),
                new BigDecimal(100));

        objs.add(new Order(1,
                rp,
                sc,
                us,
                pl,
                LocalDateTime.now(),
                new BigInteger("30"),
                LocalDateTime.now().plusMinutes(30),
                new BigDecimal(100),
                0.0,
                new BigDecimal(100)));
        objs.add(new Order(2,
                rp,
                sc,
                us,
                pl,
                LocalDateTime.now(),
                new BigInteger("30"),
                LocalDateTime.now().plusMinutes(30),
                new BigDecimal(100),
                0.0,
                new BigDecimal(100)));


        dtoObjs.add(new DtoOrder(1,
                dtorp,
                dtosc,
                dtous,
                dtopl,
                LocalDateTime.now(),
                new BigInteger("30"),
                LocalDateTime.now().plusMinutes(30),
                new BigDecimal(100),
                0.0,
                new BigDecimal(100)));
        dtoObjs.add(new DtoOrder(2,
                dtorp,
                dtosc,
                dtous,
                dtopl,
                LocalDateTime.now().plusMinutes(60),
                new BigInteger("30"),
                LocalDateTime.now().plusMinutes(90),
                new BigDecimal(100),
                0.0,
                new BigDecimal(100)));
    }

    @AfterAll
    public static void clearTestRoles() {
        objs.clear();
        dtoObjs.clear();
    }

    @Test
    void create() throws HandleNotModified, HandleBadRequestPath, HandleBadCondition, HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(serviceRentalPoint).when(((ServiceOrder) serviceObj)).getServiceRentalPoint();
        doReturn(serviceScooter).when(((ServiceOrder) serviceObj)).getServiceScooter();
        doReturn(serviceUser).when(((ServiceOrder) serviceObj)).getServiceUser();
        doReturn(servicePriceList).when(((ServiceOrder) serviceObj)).getServicePriceList();
        doReturn(repoScooter).when(serviceScooter).getMySqlRepo();
        doReturn(serviceScooterModel).when(serviceScooter).getServiceScooterModel();
        doReturn(serviceRentalPoint).when(serviceScooter).getServiceRentalPoint();

        when(repo.create(any(Order.class))).thenReturn(true);
        when(repoScooter.read(any(Integer.class))).thenReturn(sc);
        when(repoScooter.update(any(Scooter.class))).thenReturn(true);
        when(serviceUser.checkObject(any(User.class),any(boolean.class))).thenReturn(true);
        when(servicePriceList.checkObject(any(PriceList.class),any(boolean.class))).thenReturn(true);
        when(serviceRentalPoint.checkObject(any(RentalPoint.class),any(boolean.class))).thenReturn(true);
        when(serviceScooterModel.checkObject(any(ScooterModel.class),any(boolean.class))).thenReturn(true);

        Assertions.assertTrue(serviceObj.create(objs.get(0)));
        sc.setScooterStatus(ScooterStatus.AVAILABLE);
        Assertions.assertNotNull(serviceObj.create(objs.get(1)));
        sc.setScooterStatus(ScooterStatus.AVAILABLE);
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.create(null));
    }

    @Test
    void update() throws HandleBadCondition, HandleNotModified {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(serviceRentalPoint).when(((ServiceOrder) serviceObj)).getServiceRentalPoint();
        doReturn(serviceScooter).when(((ServiceOrder) serviceObj)).getServiceScooter();
        doReturn(serviceUser).when(((ServiceOrder) serviceObj)).getServiceUser();
        doReturn(servicePriceList).when(((ServiceOrder) serviceObj)).getServicePriceList();
        doReturn(repoScooter).when(serviceScooter).getMySqlRepo();
        doReturn(serviceScooterModel).when(serviceScooter).getServiceScooterModel();
        doReturn(serviceRentalPoint).when(serviceScooter).getServiceRentalPoint();

        when(repo.read(any(Integer.class))).thenReturn(objs.get(0));
        when(repo.update(any(Order.class))).thenReturn(true);
        when(repoScooter.read(any(Integer.class))).thenReturn(sc);
        when(serviceUser.checkObject(any(User.class),any(boolean.class))).thenReturn(true);
        when(servicePriceList.checkObject(any(PriceList.class),any(boolean.class))).thenReturn(true);
        when(serviceRentalPoint.checkObject(any(RentalPoint.class),any(boolean.class))).thenReturn(true);
        when(serviceScooterModel.checkObject(any(ScooterModel.class),any(boolean.class))).thenReturn(true);

        Assertions.assertTrue(serviceObj.update(objs.get(0)));
        Assertions.assertTrue(serviceObj.update(objs.get(1)));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.update(null));
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

        when(modelMapper.map(objs.get(0), DtoOrder.class)).thenReturn(dtoObjs.get(0));
        when(repo.read(1)).thenReturn(objs.get(0));

        Assertions.assertNotNull(serviceObj.read(1));
        Assertions.assertEquals(dtoObjs.get(0), serviceObj.read(1));
    }

    @Test
    void readAll() throws HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(modelMapper).when(serviceObj).getModelMapper();

        for (int i = 0; i < objs.size(); i++) {
            when(modelMapper.map(objs.get(i), DtoOrder.class)).thenReturn(dtoObjs.get(i));
        }

        when(repo.readAll()).thenReturn(objs);

        Assertions.assertNotNull(serviceObj.readAll());
        Assertions.assertEquals(dtoObjs, serviceObj.readAll());
    }

    @Test
    void checkObject() throws HandleBadCondition {
        doReturn(serviceRentalPoint).when(((ServiceOrder) serviceObj)).getServiceRentalPoint();
        doReturn(serviceScooter).when(((ServiceOrder) serviceObj)).getServiceScooter();
        doReturn(serviceUser).when(((ServiceOrder) serviceObj)).getServiceUser();
        doReturn(servicePriceList).when(((ServiceOrder) serviceObj)).getServicePriceList();
        doReturn(repoScooter).when(serviceScooter).getMySqlRepo();
        doReturn(serviceScooterModel).when(serviceScooter).getServiceScooterModel();
        doReturn(serviceRentalPoint).when(serviceScooter).getServiceRentalPoint();

        when(repoScooter.read(any(Integer.class))).thenReturn(sc);
        when(serviceUser.checkObject(any(User.class),any(boolean.class))).thenReturn(true);
        when(servicePriceList.checkObject(any(PriceList.class),any(boolean.class))).thenReturn(true);
        when(serviceRentalPoint.checkObject(any(RentalPoint.class),any(boolean.class))).thenReturn(true);
        when(serviceScooterModel.checkObject(any(ScooterModel.class),any(boolean.class))).thenReturn(true);

        Assertions.assertTrue(serviceObj.checkObject(objs.get(0), false));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.checkObject(null, false));
    }
}