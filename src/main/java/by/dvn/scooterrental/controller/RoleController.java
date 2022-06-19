package by.dvn.scooterrental.controller;

import by.dvn.scooterrental.model.account.Role;
import by.dvn.scooterrental.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {
    private AbstractService<Role> objService;

    @Autowired
    public RoleController(AbstractService<Role> objService) {
        this.objService = objService;
    }

    @GetMapping(value = "/role")
    public ResponseEntity readAllObj() {

        List<Role> objList = objService.readAll();

        return ResponseEntity.ok(objList);//new ResponseEntity<>(objList, HttpStatus.OK);
    }

}
