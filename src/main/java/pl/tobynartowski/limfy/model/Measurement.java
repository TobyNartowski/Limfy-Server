package pl.tobynartowski.limfy.model;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
public class Measurement implements Serializable {

    private static final long serialVersionUID = 6152801670809690582L;

    @Id
    @GeneratedValue
    @Column(columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @NotNull
    private Integer heartbeat;

    @NotNull
    private Integer steps;

    @NotNull
    private Integer shakiness;

    @NotNull
    private Date timestamp = new Date();

    @JsonBackReference
    @ManyToOne
    private User user;

    public Measurement() {}

    public Measurement(@NotNull Integer heartbeat, @NotNull Integer steps, @NotNull Integer shakiness) {
        this.heartbeat = heartbeat;
        this.steps = steps;
        this.shakiness = shakiness;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Integer heartbeat) {
        this.heartbeat = heartbeat;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Integer getShakiness() {
        return shakiness;
    }

    public void setShakiness(Integer shakiness) {
        this.shakiness = shakiness;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NotNull Date timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
