package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.account.DtoUser;
import by.dvn.scooterrental.dto.rental.*;
import by.dvn.scooterrental.handlerexception.HandleBadCondition;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.handlerexception.HandleNotModified;
import by.dvn.scooterrental.model.account.User;
import by.dvn.scooterrental.model.rental.*;
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

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceSeasonTicketTest {
    @Mock
    private MySqlRepoSeasonTicket repo;
    @Mock
    private ModelMapper modelMapper = new ModelMapper();
    @Mock
    private MySqlRepoPriceType repoPriceType;
    @Mock
    private MySqlRepoUser repoUser;

    @Spy
    private ServicePriceType servicePriceType = new ServicePriceType(repoPriceType, modelMapper);
    @Spy
    private ServiceUser serviceUser = new ServiceUser(repoUser, modelMapper);
    @Spy
    private AbstractService serviceObj = new ServiceSeasonTicket(repo, modelMapper);

    private static List<SeasonTicket> objs;
    private static List<DtoSeasonTicket> dtoObjs;
    private static PriceType pt;
    private static PriceType pt1;
    private static User us;
    private static DtoPriceType dtopt;
    private static DtoUser dtous;

    @BeforeAll
    public static void initData() {
        objs = new ArrayList<>();
        dtoObjs = new ArrayList<>();

        us = new User(1, "user1", "1", "2", "3", "4", 22, new ArrayList<>());
        pt = new PriceType(1, "pt1", new BigInteger("100"), false);
        pt1 = new PriceType(2, "pt2", new BigInteger("100"), true);

        objs.add(new SeasonTicket(1, LocalDate.now(), pt, us));
        objs.add(new SeasonTicket(2, LocalDate.now().minusDays(1), pt, us));
        dtoObjs.add(new DtoSeasonTicket(1, LocalDate.now(), dtopt, dtous));
        dtoObjs.add(new DtoSeasonTicket(2, LocalDate.now().minusDays(1), dtopt, dtous));
    }

    @AfterAll
    public static void clearTestRoles() {
        objs.clear();
        dtoObjs.clear();
    }

    @Test
    void create() throws HandleNotModified, HandleBadRequestPath, HandleBadCondition, HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(servicePriceType).when(((ServiceSeasonTicket) serviceObj)).getServicePriceType();
        doReturn(serviceUser).when(((ServiceSeasonTicket) serviceObj)).getServiceUser();
        doReturn(repoPriceType).when(servicePriceType).getMySqlRepo();
        doReturn(repoUser).when(serviceUser).getMySqlRepo();

        when(repo.create(any(SeasonTicket.class))).thenReturn(true);
        when(repoPriceType.read(any(Integer.class))).thenReturn(pt);
        when(repoUser.read(any(Integer.class))).thenReturn(us);

        Assertions.assertTrue(serviceObj.create(objs.get(0)));
        Assertions.assertNotNull(serviceObj.create(objs.get(0)));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.create(null));
    }

    @Test
    void update() throws HandleNotModified, HandleBadCondition {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(servicePriceType).when(((ServiceSeasonTicket) serviceObj)).getServicePriceType();
        doReturn(serviceUser).when(((ServiceSeasonTicket) serviceObj)).getServiceUser();
        doReturn(repoPriceType).when(servicePriceType).getMySqlRepo();
        doReturn(repoUser).when(serviceUser).getMySqlRepo();

        objs.get(0).setPriceType(pt1);

        when(repo.read(any(Integer.class))).thenReturn(objs.get(0));
        when(repo.update(any(SeasonTicket.class))).thenReturn(true);
        when(repoPriceType.read(any(Integer.class))).thenReturn(pt);
        when(repoUser.read(any(Integer.class))).thenReturn(us);

        Assertions.assertTrue(serviceObj.update(objs.get(0)));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.update(null));
        objs.get(0).setPriceType(pt);
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

        when(modelMapper.map(objs.get(0), DtoSeasonTicket.class)).thenReturn(dtoObjs.get(0));
        when(repo.read(1)).thenReturn(objs.get(0));

        Assertions.assertNotNull(serviceObj.read(1));
        Assertions.assertEquals(dtoObjs.get(0), serviceObj.read(1));
    }

    @Test
    void readAll() throws HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(modelMapper).when(serviceObj).getModelMapper();

        for (int i = 0; i < objs.size(); i++) {
            when(modelMapper.map(objs.get(i), DtoSeasonTicket.class)).thenReturn(dtoObjs.get(i));
        }

        when(repo.readAll()).thenReturn(objs);

        Assertions.assertNotNull(serviceObj.readAll());
        Assertions.assertEquals(dtoObjs, serviceObj.readAll());
    }

    @Test
    void checkObject() throws HandleBadCondition {
        doReturn(servicePriceType).when(((ServiceSeasonTicket) serviceObj)).getServicePriceType();
        doReturn(serviceUser).when(((ServiceSeasonTicket) serviceObj)).getServiceUser();
        doReturn(repoPriceType).when(servicePriceType).getMySqlRepo();
        doReturn(repoUser).when(serviceUser).getMySqlRepo();

        when(repoPriceType.read(any(Integer.class))).thenReturn(pt);
        when(repoUser.read(any(Integer.class))).thenReturn(us);

        Assertions.assertTrue(serviceObj.checkObject(objs.get(0), false));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.checkObject(null, false));
    }
}