package by.dvn.scooterrental.controller.account;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.account.DtoUser;
import by.dvn.scooterrental.dto.viewreport.ViewOrderInfo;
import by.dvn.scooterrental.handlerexception.*;
import by.dvn.scooterrental.model.account.User;
import by.dvn.scooterrental.service.account.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private ServiceUser objService;

    @Autowired
    public UserController(ServiceUser objService) {
        this.objService = objService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity readObj(@PathVariable(required = true, name = "id") Integer id)
            throws HandleBadRequestPath, HandleNotFoundExeption {

        DtoUser obj = (DtoUser) objService.read(id);

        return ResponseEntity.ok(obj);
    }

    @GetMapping
    public ResponseEntity readAllObj() throws HandleNotFoundExeption {

        List<IDtoObject> objList = objService.readAll();

        return ResponseEntity.ok(objList);
    }

    @GetMapping(value = "/info/{id}")
    public ResponseEntity readUserInfo(@PathVariable(required = true, name = "id") Integer id)
            throws HandleBadRequestPath, HandleNotFoundExeption {

        List<ViewOrderInfo> obj = objService.getInfoUserRental(id);

        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity createObj(@RequestBody User obj)
            throws HandleBadRequestBody, HandleBadCondition, HandleNotModified {

        if (obj == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (objService.create(obj)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping
    public ResponseEntity updateObj(@RequestBody User obj)
            throws HandleBadRequestBody, HandleBadCondition, HandleNotModified {

        if (obj == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (objService.update(obj)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteObj(@PathVariable(required = true, name = "id") Integer id)
            throws HandleBadRequestPath, HandleNotFoundExeption {

        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (objService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
