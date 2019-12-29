package pl.tobynartowski.limfy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.tobynartowski.limfy.model.persitent.BodyData;
import pl.tobynartowski.limfy.model.wrapper.BodyDataWrapper;
import pl.tobynartowski.limfy.repository.BodyDataRepository;

import java.util.UUID;

@RepositoryRestController
public class BodyDataController {

    private BodyDataRepository bodyDataRepository;

    @Autowired
    public BodyDataController(BodyDataRepository bodyDataRepository) {
        this.bodyDataRepository = bodyDataRepository;
    }

    @PatchMapping(value = "/users/{id}/body-data", produces = MediaTypes.HAL_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<BodyData>> updateBodyData(@PathVariable("id") String id,
                                                                @RequestBody BodyDataWrapper data,
                                                                HttpRequest request) {
        BodyData originalBodyData = bodyDataRepository.findByUserId(UUID.fromString(id));
        originalBodyData.setHeight(data.getHeight());
        originalBodyData.setWeight(data.getWeight());
        originalBodyData.setGender(data.getGender());
        bodyDataRepository.save(originalBodyData);

        String resourceBaseURL = request.getURI().toString();
        resourceBaseURL = resourceBaseURL.substring(0, resourceBaseURL.indexOf("users"));

        EntityModel<BodyData> responseModel = new EntityModel<>(originalBodyData);
        responseModel.add(new Link(resourceBaseURL + "body-data/" + originalBodyData.getId(), "self"));
        responseModel.add(new Link(resourceBaseURL + "body-data/" + originalBodyData.getId(), "bodyData"));
        responseModel.add(new Link(resourceBaseURL + "users/" + id, "user"));
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
}
