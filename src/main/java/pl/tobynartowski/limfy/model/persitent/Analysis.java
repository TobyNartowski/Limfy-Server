package pl.tobynartowski.limfy.model.persitent;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
public class Analysis implements Serializable {

    private static final long serialVersionUID = -6392674144633203488L;

    @Id
    @GeneratedValue
    @Column(columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @JsonManagedReference
    @NotNull
    @ManyToOne
    private Disease disease;

    @NotNull
    @Range(min = -100, max = 100)
    private Double percentage;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    private Date timestamp = new Date();

    public Analysis() {}

    public Analysis(@NotNull Disease disease, @NotNull @Range(min = -100, max = 100) Double percentage, @NotNull User user) {
        this.disease = disease;
        this.percentage = percentage;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
