package by.dvn.scooterrental.service.account;

import by.dvn.scooterrental.dto.account.DtoRole;
import by.dvn.scooterrental.handlerexception.HandleBadCondition;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.handlerexception.HandleNotModified;
import by.dvn.scooterrental.model.account.Role;
import by.dvn.scooterrental.repository.account.MySqlRepoRole;
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
class ServiceRoleTest {
    @Mock
    private MySqlRepoRole repo;
    @Mock
    private ModelMapper modelMapper = new ModelMapper();

    @Spy
    private AbstractService serviceObj = new ServiceRole(repo, modelMapper);

    private static List<Role> objs;
    private static List<DtoRole> dtoObjs;

    @BeforeAll
    public static void initData() {
        objs = new ArrayList<>();
        dtoObjs = new ArrayList<>();

        objs.add(new Role(1, "ROLE_ADMIN"));
        objs.add(new Role(2, "ROLE_USER"));
        dtoObjs.add(new DtoRole(1, "ROLE_ADMIN"));
        dtoObjs.add(new DtoRole(2, "ROLE_USER"));

    }

    @AfterAll
    public static void clearTestRoles() {
        objs.clear();
        dtoObjs.clear();
    }


    @Test
    void create() throws HandleNotModified, HandleBadRequestPath, HandleBadCondition, HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();

        when(repo.create(any(Role.class))).thenReturn(true);

        Assertions.assertTrue(serviceObj.create(objs.get(0)));
        Assertions.assertNotNull(serviceObj.create(objs.get(0)));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.create(null));
    }

    @Test
    void update() throws HandleNotModified, HandleBadCondition {
        doReturn(repo).when(serviceObj).getMySqlRepo();

        objs.get(0).setName("ROLE_NOT");
        Role objToFalse = new Role(4, "ROLE_NOT");

        when(repo.update(objs.get(0))).thenReturn(true);
        when(repo.read(1)).thenReturn(objs.get(0));

        Assertions.assertTrue(serviceObj.update(objs.get(0)));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.update(objToFalse));
        objs.get(0).setName("ROLE_ADMIN");
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

        when(modelMapper.map(objs.get(0), DtoRole.class)).thenReturn(dtoObjs.get(0));
        when(repo.read(1)).thenReturn(objs.get(0));

        Assertions.assertNotNull(serviceObj.read(1));
        Assertions.assertEquals(dtoObjs.get(0), serviceObj.read(1));
    }

    @Test
    void readAll() throws HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(modelMapper).when(serviceObj).getModelMapper();

        for (int i = 0; i < objs.size(); i++) {
            when(modelMapper.map(objs.get(i), DtoRole.class)).thenReturn(dtoObjs.get(i));
        }

        when(repo.readAll()).thenReturn(objs);

        Assertions.assertNotNull(serviceObj.readAll());
        Assertions.assertEquals(dtoObjs, serviceObj.readAll());
    }

    @Test
    void findByName() {
        doReturn(repo).when(serviceObj).getMySqlRepo();

        when(repo.findByName(objs.get(0).getName())).thenReturn(objs.get(0));

        Assertions.assertNotNull(((ServiceRole) serviceObj).findByName(objs.get(0).getName()));
        Assertions.assertEquals(objs.get(0), ((ServiceRole) serviceObj).findByName("ROLE_ADMIN"));
        Assertions.assertNotEquals(objs.get(0), ((ServiceRole) serviceObj).findByName("ROLE_USER"));
    }

    @Test
    void checkObject() throws HandleBadCondition {
        Assertions.assertTrue(serviceObj.checkObject(objs.get(0), false));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.checkObject(null, false));
    }

}