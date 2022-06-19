package by.dvn.scooterrental.controller;

import by.dvn.scooterrental.dto.rental.DtoPriceType;
import by.dvn.scooterrental.model.rental.PriceType;
import by.dvn.scooterrental.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PriceTypeController {
    private AbstractService<PriceType> objService;

    @Autowired
    public PriceTypeController(AbstractService<PriceType> objService) {
        this.objService = objService;
    }

    @GetMapping(value = "/price-type")
    public ResponseEntity readAllObj() {

        List<DtoPriceType> objList = objService.readAll();

        return ResponseEntity.ok(objList);//new ResponseEntity<>(objList, HttpStatus.OK);
    }

}
