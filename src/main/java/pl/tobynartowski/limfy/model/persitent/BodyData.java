package pl.tobynartowski.limfy.model.persitent;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
public class BodyData implements Serializable {

    private static final long serialVersionUID = 6778794138531954221L;

    @Id
    @GeneratedValue
    @Column(columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    private Boolean gender;
    private Float weight;
    private Float height;
    private Integer age;

    @NotNull
    private Date timestamp = new Date();

    @JsonBackReference
    @ManyToOne
    private User user;

    public BodyData() {}

    public BodyData(Boolean gender, Float weight, Float height, Integer age) {
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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
