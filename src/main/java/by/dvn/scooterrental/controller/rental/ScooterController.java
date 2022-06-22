package by.dvn.scooterrental.controller.rental;

import by.dvn.scooterrental.dto.rental.DtoScooter;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.model.rental.Scooter;
import by.dvn.scooterrental.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scooter")
public class ScooterController {
    private AbstractService<Scooter> objService;

    @Autowired
    public ScooterController(AbstractService<Scooter> objService) {
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

        List<DtoScooter> objList = objService.readAll();

        return ResponseEntity.ok(objList);
    }

    @PostMapping
    public ResponseEntity createObj(@RequestBody Scooter obj) {

        if (obj == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (objService.create(obj)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping
    public ResponseEntity updateObj(@RequestBody Scooter obj) {

        if (obj == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (objService.update(obj)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteObj(@PathVariable(required = true, name = "id") Integer id) {

        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (objService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
