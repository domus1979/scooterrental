package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.account.DtoRole;
import by.dvn.scooterrental.dto.account.DtoUser;
import by.dvn.scooterrental.dto.rental.DtoPriceList;
import by.dvn.scooterrental.dto.rental.DtoPriceType;
import by.dvn.scooterrental.dto.rental.DtoScooter;
import by.dvn.scooterrental.dto.rental.DtoScooterModel;
import by.dvn.scooterrental.handlerexception.HandleBadCondition;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.handlerexception.HandleNotModified;
import by.dvn.scooterrental.model.account.Role;
import by.dvn.scooterrental.model.account.User;
import by.dvn.scooterrental.model.rental.PriceList;
import by.dvn.scooterrental.model.rental.PriceType;
import by.dvn.scooterrental.model.rental.ScooterModel;
import by.dvn.scooterrental.repository.account.MySqlRepoRole;
import by.dvn.scooterrental.repository.account.MySqlRepoUser;
import by.dvn.scooterrental.repository.rental.MySqlRepoPriceList;
import by.dvn.scooterrental.repository.rental.MySqlRepoPriceType;
import by.dvn.scooterrental.repository.rental.MySqlRepoScooterModel;
import by.dvn.scooterrental.service.AbstractService;
import by.dvn.scooterrental.service.account.ServiceRole;
import by.dvn.scooterrental.service.account.ServiceUser;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServicePriceListTest {
    @Mock
    private MySqlRepoPriceList repo;
    @Mock
    private ModelMapper modelMapper = new ModelMapper();
    @Mock
    private MySqlRepoScooterModel repoScooterModel;
    @Mock
    private MySqlRepoPriceType repoPriceType;

    @Spy
    private ServicePriceType servicePriceType = new ServicePriceType(repoPriceType, modelMapper);
    @Spy
    private ServiceScooterModel serviceScooterModel = new ServiceScooterModel(repoScooterModel, modelMapper);
    @Spy
    private AbstractService serviceObj = new ServicePriceList(repo, modelMapper);

    private static List<PriceList> objs;
    private static List<DtoPriceList> dtoObjs;
    private static PriceType pt;
    private static PriceType pt1;
    private static DtoPriceType dtopt;
    private static DtoPriceType dtopt1;
    private static ScooterModel sm;
    private static DtoScooterModel dtosm;

    @BeforeAll
    public static void initData() {
        objs = new ArrayList<>();
        dtoObjs = new ArrayList<>();

        pt = new PriceType(1, "opt", new BigInteger("1"), false);
        pt1 = new PriceType(2, "opt1", new BigInteger("1"), false);
        dtopt = new DtoPriceType(1, "opt", new BigInteger("1"), false);
        dtopt1 = new DtoPriceType(2, "opt1", new BigInteger("1"), false);

        sm = new ScooterModel(1, "model", 1, 1, 1);
        dtosm = new DtoScooterModel(1, "model", 1, 1, 1);

        objs.add(new PriceList(1, sm, pt, new BigDecimal("100")));
        objs.add(new PriceList(2, sm, pt1, new BigDecimal("200")));

        dtoObjs.add(new DtoPriceList(1, dtosm, dtopt, new BigDecimal("100")));
        dtoObjs.add(new DtoPriceList(2, dtosm, dtopt1, new BigDecimal("200")));
    }

    @AfterAll
    public static void clearTestRoles() {
        objs.clear();
        dtoObjs.clear();
    }

    @Test
    void create() throws HandleNotModified, HandleBadRequestPath, HandleBadCondition, HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(serviceScooterModel).when(((ServicePriceList) serviceObj)).getServiceScooterModel();
        doReturn(servicePriceType).when(((ServicePriceList) serviceObj)).getServicePriceType();
        doReturn(repoScooterModel).when(serviceScooterModel).getMySqlRepo();
        doReturn(repoPriceType).when(servicePriceType).getMySqlRepo();

        when(repo.create(any(PriceList.class))).thenReturn(true);
        when(repoScooterModel.read(any(Integer.class))).thenReturn(sm);
        when(repoPriceType.read(any(Integer.class))).thenReturn(pt);

        Assertions.assertTrue(serviceObj.create(objs.get(0)));
        Assertions.assertNotNull(serviceObj.create(objs.get(0)));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.create(null));
    }

    @Test
    void update() throws HandleNotModified, HandleBadCondition {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(serviceScooterModel).when(((ServicePriceList) serviceObj)).getServiceScooterModel();
        doReturn(servicePriceType).when(((ServicePriceList) serviceObj)).getServicePriceType();
        doReturn(repoScooterModel).when(serviceScooterModel).getMySqlRepo();
        doReturn(repoPriceType).when(servicePriceType).getMySqlRepo();

        objs.get(0).setPriceType(pt1);

        when(repo.read(any(Integer.class))).thenReturn(objs.get(0));
        when(repo.update(any(PriceList.class))).thenReturn(true);
        when(repoScooterModel.read(any(Integer.class))).thenReturn(objs.get(0).getScooterModel());
        when(repoPriceType.read(any(Integer.class))).thenReturn(objs.get(0).getPriceType());

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

        when(modelMapper.map(objs.get(0), DtoPriceList.class)).thenReturn(dtoObjs.get(0));
        when(repo.read(1)).thenReturn(objs.get(0));

        Assertions.assertNotNull(serviceObj.read(1));
        Assertions.assertEquals(dtoObjs.get(0), serviceObj.read(1));
    }

    @Test
    void readAll() throws HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(modelMapper).when(serviceObj).getModelMapper();

        for (int i = 0; i < objs.size(); i++) {
            when(modelMapper.map(objs.get(i), DtoPriceList.class)).thenReturn(dtoObjs.get(i));
        }

        when(repo.readAll()).thenReturn(objs);

        Assertions.assertNotNull(serviceObj.readAll());
        Assertions.assertEquals(dtoObjs, serviceObj.readAll());
    }

    @Test
    void checkObject() throws HandleBadCondition {
        doReturn(serviceScooterModel).when(((ServicePriceList) serviceObj)).getServiceScooterModel();
        doReturn(servicePriceType).when(((ServicePriceList) serviceObj)).getServicePriceType();
        doReturn(repoScooterModel).when(serviceScooterModel).getMySqlRepo();
        doReturn(repoPriceType).when(servicePriceType).getMySqlRepo();

        when(repoScooterModel.read(any(Integer.class))).thenReturn(sm);
        when(repoPriceType.read(any(Integer.class))).thenReturn(pt);

        Assertions.assertTrue(serviceObj.checkObject(objs.get(0), false));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.checkObject(null, false));
    }
}