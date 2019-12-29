package pl.tobynartowski.limfy.controller;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.tobynartowski.limfy.model.persitent.Measurement;
import pl.tobynartowski.limfy.model.wrapper.MeasurementProjection;
import pl.tobynartowski.limfy.model.wrapper.MeasurementWrapper;
import pl.tobynartowski.limfy.repository.MeasurementRepository;

import java.util.List;
import java.util.stream.Collectors;

@RepositoryRestController
public class MeasurementController {

    private MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementController(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @GetMapping(value = "/users/{id}/measurements/average", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PagedModel<EntityModel<MeasurementWrapper>>> findHeartbeatAverages(@PathVariable String id, Pageable pageable,
                                                                                             HttpRequest request) {
        String resourceBaseURL = request.getURI().toString();
        resourceBaseURL = resourceBaseURL.substring(0, resourceBaseURL.indexOf("average") + 7);

        Page<MeasurementProjection> page = measurementRepository.findMeasurementAverages(id, pageable);
        List<EntityModel<MeasurementWrapper>> wrappedPage = page.stream()
                .map(m -> new EntityModel<>(new MeasurementWrapper(m.getHeartbeatAverage(), m.getStepsSum(), m.getTimestamp())))
                .collect(Collectors.toList());

        PagedModel<EntityModel<MeasurementWrapper>> pagedModel = new PagedModel<>(wrappedPage,
                new PagedModel.PageMetadata(pageable.getPageSize(), pageable.getPageNumber(), page.getTotalElements()));

        pagedModel.add(new Link(resourceBaseURL + "?page=0&size=" + pageable.getPageSize(), "first"));
        if (pageable.getPageNumber() > 0) {
            pagedModel.add(new Link(resourceBaseURL + "?page=" + (pageable.getPageNumber() - 1) + "&size=" + pageable.getPageSize(), "prev"));
        }
        pagedModel.add(new Link(resourceBaseURL + "?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(), "self"));
        if (pageable.getPageNumber() < page.getTotalPages() - 1) {
            pagedModel.add(new Link(resourceBaseURL + "?page=" + (pageable.getPageNumber() + 1) + "&size=" + pageable.getPageSize(), "next"));
        }
        pagedModel.add(new Link(resourceBaseURL + "?page=" + (page.getTotalPages() - 1) + "&size=" + pageable.getPageSize(), "last"));

        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @PostMapping(value = "/measurements")
    public ResponseEntity<JSONObject> postMeasurement(@RequestBody EntityModel<Measurement> measurement) {
        if (measurement.getContent() == null || measurement.getContent().getUser() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        measurementRepository.save(measurement.getContent());

        Double heartbeat = measurementRepository.getTodayHeartbeatAverage(measurement.getContent().getUser().getId().toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("heartbeat", heartbeat);

        return new ResponseEntity<>(jsonObject, HttpStatus.CREATED);
    }
}
