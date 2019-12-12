package pl.tobynartowski.limfy.model.persitent;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Range;

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

    @NotNull
    private Boolean gender;

    @NotNull
    @Range(min = 30, max = 150)
    private Integer weight;

    @NotNull
    @Range(min = 100, max = 250)
    private Integer height;

    @NotNull
    @Range(min = 6, max = 128)
    private Integer age;

    @NotNull
    private Date timestamp = new Date();

    @JsonBackReference
    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    public BodyData() {}

    public BodyData(@NotNull Boolean gender, @NotNull Integer weight, @NotNull Integer height, @NotNull Integer age) {
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(@NotNull Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(@NotNull Integer height) {
        this.height = height;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(@NotNull Integer age) {
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
