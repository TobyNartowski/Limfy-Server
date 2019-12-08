package pl.tobynartowski.limfy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.tobynartowski.limfy.model.projection.MeasurementProjection;
import pl.tobynartowski.limfy.repository.MeasurementRepository;

import java.util.List;

@RepositoryRestController
public class MeasurementController {

    private MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementController(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @GetMapping(value = "/users/{id}/measurements/average", produces = "application/hal+json")
    public ResponseEntity<List<MeasurementProjection>> findHeartbeatAverages(@PathVariable String id) {
        if (id == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        List<MeasurementProjection> fetchedMeasurements = measurementRepository.findHeartbeatMeasurements(id);
        return new ResponseEntity<>(fetchedMeasurements, HttpStatus.OK);
    }
}
