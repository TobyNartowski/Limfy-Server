package pl.tobynartowski.limfy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.tobynartowski.limfy.model.User;
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

    @PostMapping(value = "/users", produces = "application/hal+json")
    public ResponseEntity<EntityModel<User>> saveUser(@RequestBody User user, HttpRequest request) {
        if (userRepository.findUserByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        String resourceURL = request.getURI().toString() + "/" + savedUser.getId();
        EntityModel<User> resource = new EntityModel<>(savedUser);
        resource.add(
                new Link(resourceURL, "self"),
                new Link(resourceURL, "user"),
                new Link(resourceURL + "/measurements", "measurements"),
                new Link(resourceURL + "/body_data", "body_data")
        );
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }
}
