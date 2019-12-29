package pl.tobynartowski.limfy.controller;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.tobynartowski.limfy.model.persitent.User;
import pl.tobynartowski.limfy.repository.UserRepository;

@RepositoryRestController
public class UserController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/users", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<User>> saveUser(@RequestBody User user, HttpRequest request) {
        if (userRepository.findUserByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        if (user.getUsername() == null || user.getPassword() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        String resourceURL = request.getURI().toString();
        if (!request.getURI().toString().endsWith("/")) {
            resourceURL += "/";
        }
        resourceURL += savedUser.getId();

        EntityModel<User> resource = new EntityModel<>(savedUser);
        resource.add(
                new Link(resourceURL, "self"),
                new Link(resourceURL, "user"),
                new Link(resourceURL + "/measurements", "measurements"),
                new Link(resourceURL + "/body_data", "body_data")
        );
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping(value = "/users/username/{username}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<JSONObject> getUserId(@PathVariable String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        JSONObject object = new JSONObject();
        object.put("id", user.getId());
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{username}")
    public ResponseEntity<String> deleteEmptyUser(@PathVariable String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (user.getBodyData() != null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
        }

        userRepository.delete(user);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
