package by.bsuir.spp.controller;

import by.bsuir.spp.exceptions.DbException;
import by.bsuir.spp.exceptions.EntityNotFoundException;
import by.bsuir.spp.entities.Offer;
import by.bsuir.spp.payload.DeletingArray;
import by.bsuir.spp.service.OfferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class OfferController {

    @Autowired
    private OfferService service;

    private final static Logger logger = LogManager.getLogger(OfferController.class);

    @GetMapping("/offers")
    public ResponseEntity getAll() throws DbException {
        List list = service.getAll();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity getById(@PathVariable Long id) throws DbException {
        Offer offer = service.getById(id);
        if (offer != null) {
            return new ResponseEntity<>(offer, HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("Unable to find offer with id = " + id);
        }
    }

    @PutMapping("/offers/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Offer offer) throws DbException {
        offer = service.update(id, offer);
        if (offer != null) {
            logger.info("Updated offer with id = " + id);
            return new ResponseEntity<>(offer, HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("Unable to find offer with id = " + id);
        }
    }

    @PostMapping("/offers")
    public ResponseEntity<Offer> create(@RequestBody Offer offer) throws DbException {
        offer = service.create(offer);
        logger.info("Created new Offer with id = " + offer.getId());
        return new ResponseEntity<>(offer, HttpStatus.CREATED);
    }

    @DeleteMapping("/offers/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws DbException {
        if (service.delete(id)) {
            logger.info("Deleted offer with id = " + id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("Unable to find offer with id = " + id);
        }
    }

    //In this case we are deleting by POST request because Angular HttpClient not supported
    //body in DELETE request
    @PostMapping("/offers/array")
    public ResponseEntity deleteMany(@RequestBody DeletingArray array) throws DbException {
        if (service.deleteByArray(array.getArray())) {
            logger.info("Deleted offers");
            return new ResponseEntity(HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("Unable to find offers");
        }
    }
}
