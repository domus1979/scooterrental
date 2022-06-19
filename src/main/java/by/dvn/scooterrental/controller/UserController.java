package by.dvn.scooterrental.controller;

import by.dvn.scooterrental.model.account.User;
import by.dvn.scooterrental.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private AbstractService<User> objService;

    @Autowired
    public UserController(AbstractService<User> objService) {
        this.objService = objService;
    }

    @GetMapping(value = "/user")
    public ResponseEntity readAllObj() {

        List<User> objList = objService.readAll();

        return ResponseEntity.ok(objList);//new ResponseEntity<>(objList, HttpStatus.OK);
    }

}
