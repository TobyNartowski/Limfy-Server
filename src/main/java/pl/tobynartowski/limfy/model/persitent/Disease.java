package pl.tobynartowski.limfy.model.persitent;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Disease implements Serializable {

    private static final long serialVersionUID = 4525897298689910531L;

    @Id
    @GeneratedValue
    @Column(columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @NotBlank
    @Length(min = 3, max = 256)
    private String name;

    @NotNull
    @Range(min = 1, max = 100)
    private Integer healthInfluence;

    @JsonBackReference
    @OneToMany
    private Set<Analysis> analyses = new HashSet<>();

    @JsonManagedReference
    @RestResource(rel = "risk-factors", path = "risk-factors")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "risk_factor_diseases",
        joinColumns = @JoinColumn(name = "disease_id"),
        inverseJoinColumns = @JoinColumn(name = "risk_factor_id"))
    private Set<RiskFactor> riskFactors = new HashSet<>();

    public Disease() {}

    public Disease(@NotBlank @Length(min = 3, max = 256) String name, @NotNull @Range(min = 1, max = 100) Integer healthInfluence) {
        this.name = name;
        this.healthInfluence = healthInfluence;
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

    public Integer getHealthInfluence() {
        return healthInfluence;
    }

    public void setHealthInfluence(Integer healthInfluence) {
        this.healthInfluence = healthInfluence;
    }

    public Set<Analysis> getAnalyses() {
        return analyses;
    }

    public void setAnalyses(Set<Analysis> analyses) {
        this.analyses = analyses;
    }

    public Set<RiskFactor> getRiskFactors() {
        return riskFactors;
    }

    public void setRiskFactors(Set<RiskFactor> riskFactors) {
        this.riskFactors = riskFactors;
    }

    public void addRiskFactor(RiskFactor riskFactor) {
        riskFactors.add(riskFactor);
    }
}
