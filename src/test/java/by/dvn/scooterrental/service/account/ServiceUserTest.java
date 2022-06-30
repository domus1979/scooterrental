package by.dvn.scooterrental.service.account;

import by.dvn.scooterrental.dto.account.DtoRole;
import by.dvn.scooterrental.dto.account.DtoUser;
import by.dvn.scooterrental.handlerexception.HandleBadCondition;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.handlerexception.HandleNotModified;
import by.dvn.scooterrental.model.account.Role;
import by.dvn.scooterrental.model.account.User;
import by.dvn.scooterrental.repository.account.MySqlRepoRole;
import by.dvn.scooterrental.repository.account.MySqlRepoUser;
import by.dvn.scooterrental.service.AbstractService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceUserTest {
    @Mock
    private MySqlRepoUser repo;
    @Mock
    private ModelMapper modelMapper = new ModelMapper();
    @Mock
    private MySqlRepoRole repoRole;

    @Spy
    private ServiceRole serviceRole = new ServiceRole(repoRole, modelMapper);
    @Spy
    private AbstractService serviceObj = new ServiceUser(repo, modelMapper);

    private static List<User> objs;
    private static List<DtoUser> dtoObjs;
    private static List<Role> objs1;
    private static List<DtoRole> dtoObjs1;

    @BeforeAll
    public static void initData() {
        objs = new ArrayList<>();
        dtoObjs = new ArrayList<>();

        objs1 = new ArrayList<>();
        dtoObjs1 = new ArrayList<>();

        objs1.add(new Role(1, "ROLE_ADMIN"));
        dtoObjs1.add(new DtoRole(1, "ROLE_ADMIN"));

        objs.add(new User(1,
                "admin",
                "$2a$12$KxUrnotEfXmtJzJ5UDLu3edo88jjgiacgNKQ.VEl//Nq9ZBgfXWT6",
                "Kolas",
                "Yakub",
                "kolas@tut.by",
                75,
                objs1));
        objs1.add(new Role(2, "ROLE_USER"));
        objs.add(new User(2,
                "Kandrat",
                "$2a$12$PgzMqybJsm/8sVitccTYaeM9.nbDlvuPUFYJlGCFSfEjUWaTh0oPW",
                "Krapiva",
                "Kandrat",
                "krapiva@tut.by",
                64,
                objs1));

        dtoObjs.add(new DtoUser(1,
                "admin",
                "Kolas",
                "Yakub",
                "kolas@tut.by",
                75,
                dtoObjs1));
        dtoObjs1.add(new DtoRole(2, "ROLE_USER"));
        dtoObjs.add(new DtoUser(2,
                "Kandrat",
                "Krapiva",
                "Kandrat",
                "krapiva@tut.by",
                64,
                dtoObjs1));

    }

    @AfterAll
    public static void clearTestRoles() {
        objs.clear();
        dtoObjs.clear();
    }

    @Test
    void create() throws HandleNotModified, HandleBadRequestPath, HandleBadCondition, HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(serviceRole).when(((ServiceUser) serviceObj)).getServiceRole();
        doReturn(new BCryptPasswordEncoder()).when(((ServiceUser) serviceObj)).getPasswordEncoder();
        doReturn(repoRole).when(serviceRole).getMySqlRepo();

        when(repo.create(any(User.class))).thenReturn(true);
        when(repoRole.read(any(Integer.class))).thenReturn(objs.get(0).getRoleList().get(0));
        when(serviceRole.findByName(any(String.class))).thenReturn(objs.get(0).getRoleList().get(1));

        Assertions.assertTrue(serviceObj.create(objs.get(0)));
        Assertions.assertNotNull(serviceObj.create(objs.get(0)));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.create(null));
    }

    @Test
    void update() throws HandleNotModified, HandleBadCondition, HandleBadRequestPath, HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(serviceRole).when(((ServiceUser) serviceObj)).getServiceRole();
        doReturn(repoRole).when(serviceRole).getMySqlRepo();
        doReturn(new BCryptPasswordEncoder()).when(((ServiceUser) serviceObj)).getPasswordEncoder();

        objs.get(0).setLogin("Semen");

        when(repo.read(any(Integer.class))).thenReturn(objs.get(0));
        when(repo.update(any(User.class))).thenReturn(true);
        when(repoRole.read(any(Integer.class))).thenReturn(objs.get(0).getRoleList().get(0));

        Assertions.assertTrue(serviceObj.update(objs.get(0)));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.update(null));
        objs.get(0).setLogin("admin");
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

        when(modelMapper.map(objs.get(0), DtoUser.class)).thenReturn(dtoObjs.get(0));
        when(repo.read(1)).thenReturn(objs.get(0));

        Assertions.assertNotNull(serviceObj.read(1));
        Assertions.assertEquals(dtoObjs.get(0), serviceObj.read(1));
    }

    @Test
    void readAll() throws HandleNotFoundExeption {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(modelMapper).when(serviceObj).getModelMapper();

        for (int i = 0; i < objs.size(); i++) {
            when(modelMapper.map(objs.get(i), DtoUser.class)).thenReturn(dtoObjs.get(i));
        }

        when(repo.readAll()).thenReturn(objs);

        Assertions.assertNotNull(serviceObj.readAll());
        Assertions.assertEquals(dtoObjs, serviceObj.readAll());
    }

    @Test
    void findByName() {
        doReturn(repo).when(serviceObj).getMySqlRepo();

        when(repo.findByName(objs.get(0).getLogin())).thenReturn(objs.get(0));

        Assertions.assertNotNull(((ServiceUser) serviceObj).findByName(objs.get(0).getLogin()));
        Assertions.assertEquals(objs.get(0), ((ServiceUser) serviceObj).findByName("admin"));
        Assertions.assertNotEquals(objs.get(0), ((ServiceUser) serviceObj).findByName("Kandrat"));
    }

    @Test
    void checkObject() throws HandleBadCondition {
        doReturn(repo).when(serviceObj).getMySqlRepo();
        doReturn(serviceRole).when(((ServiceUser) serviceObj)).getServiceRole();
        doReturn(repoRole).when(serviceRole).getMySqlRepo();

        when(repoRole.read(any(Integer.class))).thenReturn(objs.get(0).getRoleList().get(0));

        Assertions.assertTrue(serviceObj.checkObject(objs.get(0), false));
        Assertions.assertThrowsExactly(HandleBadCondition.class, () -> serviceObj.checkObject(null, false));
    }
}