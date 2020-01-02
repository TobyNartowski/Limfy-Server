package pl.tobynartowski.limfy.model.persitent;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
public class MeasurementResult implements Serializable {

    private static final long serialVersionUID = 3672033593219357234L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID userId;

    @NotNull
    private Date timestamp;

    @NotNull
    private Double heartbeat;

    @NotNull
    private Integer steps;

    public MeasurementResult() {}

    public MeasurementResult(@NotNull UUID userId, @NotNull Date timestamp, @NotNull Double heartbeat, @NotNull Integer steps) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.heartbeat = heartbeat;
        this.steps = steps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Double heartbeat) {
        this.heartbeat = heartbeat;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }
}
