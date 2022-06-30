package by.dvn.scooterrental.controller.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.rental.DtoScooter;
import by.dvn.scooterrental.dto.viewreport.ViewOrderInfo;
import by.dvn.scooterrental.handlerexception.*;
import by.dvn.scooterrental.model.rental.Scooter;
import by.dvn.scooterrental.service.rental.ServiceScooter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scooter")
public class ScooterController {
    private ServiceScooter objService;

    @Autowired
    public ScooterController(ServiceScooter objService) {
        this.objService = objService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity readObj(@PathVariable(required = true, name = "id") Integer id)
            throws HandleBadRequestPath, HandleNotFoundExeption {

        DtoScooter obj = (DtoScooter) objService.read(id);

        return ResponseEntity.ok(obj);
    }

    @GetMapping
    public ResponseEntity readAllObj() throws HandleNotFoundExeption {

        List<IDtoObject> objList = objService.readAll();

        return ResponseEntity.ok(objList);
    }

    @GetMapping(value = "/info/{id}")
    public ResponseEntity readScooterInfo(@PathVariable(required = true, name = "id") Integer id)
            throws HandleBadRequestPath, HandleNotFoundExeption {

        List<ViewOrderInfo> obj = objService.getInfoScooterRental(id);

        return ResponseEntity.ok(obj);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity createObj(@RequestBody Scooter obj)
            throws HandleBadCondition, HandleNotModified, HandleBadRequestPath, HandleNotFoundExeption {

        if (obj == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (objService.create(obj)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public ResponseEntity updateObj(@RequestBody Scooter obj)
            throws HandleBadCondition, HandleNotModified {

        if (obj == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (objService.update(obj)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Secured("ROLE_ADMIN")
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
