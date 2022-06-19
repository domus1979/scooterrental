package by.dvn.scooterrental.dto.account;

import by.dvn.scooterrental.dto.IDtoObject;

import java.util.List;

public class DtoUser implements IDtoObject {
    private Integer id;

    private String login;

    private String firstName;

    private String lastName;

    private String email;

    private Integer age;

    private List<DtoRole> roleList;

    public DtoUser() {
    }

    public DtoUser(Integer id,
                   String login,
                   String firstName,
                   String lastName,
                   String email,
                   Integer age,
                   List<DtoRole> roleList) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.roleList = roleList;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public List<DtoRole> getRoleList() {
        return roleList;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setRoleList(List<DtoRole> roleList) {
        this.roleList = roleList;
    }

}
