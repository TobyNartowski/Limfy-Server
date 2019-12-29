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
import pl.tobynartowski.limfy.model.persitent.Contact;
import pl.tobynartowski.limfy.model.wrapper.ContactWrapper;
import pl.tobynartowski.limfy.repository.ContactRepository;

import java.util.UUID;

@RepositoryRestController
public class ContactController {

    private ContactRepository contactRepository;

    @Autowired
    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PatchMapping(value = "/users/{id}/contact", produces = MediaTypes.HAL_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Contact>> updateContact(@PathVariable("id") String id,
                                                              @RequestBody ContactWrapper contact,
                                                              HttpRequest request) {
        Contact originalContact = contactRepository.findByUserId(UUID.fromString(id));
        originalContact.setNumber(contact.getNumber());
        contactRepository.save(originalContact);

        String resourceBaseURL = request.getURI().toString();
        resourceBaseURL = resourceBaseURL.substring(0, resourceBaseURL.indexOf("users"));

        EntityModel<Contact> responseModel = new EntityModel<>(originalContact);
        responseModel.add(new Link(resourceBaseURL + "contacts/" + originalContact.getId(), "self"));
        responseModel.add(new Link(resourceBaseURL + "contacts/" + originalContact.getId(), "contact"));
        responseModel.add(new Link(resourceBaseURL + "users/" + id, "user"));
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
}