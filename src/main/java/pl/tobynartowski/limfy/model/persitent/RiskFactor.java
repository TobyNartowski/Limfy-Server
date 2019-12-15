package pl.tobynartowski.limfy.model.persitent;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import pl.tobynartowski.limfy.model.data.RiskFactorData;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class RiskFactor implements Serializable {

    private static final long serialVersionUID = 352597467631205829L;

    @Id
    @GeneratedValue
    @Column(columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @NotBlank
    @Length(min = 3, max = 256)
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "riskFactors")
    private Set<Disease> diseases = new HashSet<>();

    public RiskFactor() {}

    public RiskFactor(@NotBlank @Length(min = 3, max = 256) String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(Set<Disease> diseases) {
        this.diseases = diseases;
    }

    public void addDisease(Disease disease) {
        diseases.add(disease);
    }

    public RiskFactorData of() {
        return Arrays
                .stream(RiskFactorData.values())
                .filter(r -> r.getName().equals(this.getName()))
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }
}