package pl.tobynartowski.limfy.model.persitent;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.Type;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 5701670123455860532L;

    @Id
    @GeneratedValue
    @Column(columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @NotBlank
    @Size(max = 64)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max = 128)
    private String password;

    private String role = "ROLE_USER";

    @JsonManagedReference
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Measurement> measurements = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @RestResource(rel = "body_data", path = "body_data")
    private Set<BodyData> bodyData = new HashSet<>();

    public User() {}

    public User(@NotBlank @Size(max = 64) String username, @NotBlank String password) {
        this.username = username;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Set<Measurement> measurements) {
        this.measurements = measurements;
    }

    public Set<BodyData> getBodyData() {
        return bodyData;
    }

    public void setBodyData(Set<BodyData> bodyData) {
        this.bodyData = bodyData;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
