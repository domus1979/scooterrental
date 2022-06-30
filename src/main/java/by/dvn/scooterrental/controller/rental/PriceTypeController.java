package by.dvn.scooterrental.controller.rental;

import by.dvn.scooterrental.dto.rental.DtoPriceType;
import by.dvn.scooterrental.handlerexception.*;
import by.dvn.scooterrental.model.rental.PriceType;
import by.dvn.scooterrental.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/price-type")
public class PriceTypeController {
    private AbstractService<PriceType> objService;

    @Autowired
    public PriceTypeController(AbstractService<PriceType> objService) {
        this.objService = objService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity readObj(@PathVariable(required = true, name = "id") Integer id)
            throws HandleBadRequestPath, HandleNotFoundExeption {

        DtoPriceType obj = (DtoPriceType) objService.read(id);

        return ResponseEntity.ok(obj);
    }

    @GetMapping
    public ResponseEntity readAllObj() throws HandleNotFoundExeption {

        List<DtoPriceType> objList = objService.readAll();

        return ResponseEntity.ok(objList);
    }

    @PostMapping
    public ResponseEntity createObj(@RequestBody PriceType obj)
            throws HandleBadCondition, HandleNotModified, HandleBadRequestPath, HandleNotFoundExeption {

        if (obj == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (objService.create(obj)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping
    public ResponseEntity updateObj(@RequestBody PriceType obj)
            throws HandleBadCondition, HandleNotModified {

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
