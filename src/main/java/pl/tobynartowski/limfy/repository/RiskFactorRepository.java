package pl.tobynartowski.limfy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tobynartowski.limfy.model.persitent.RiskFactor;

import java.util.UUID;

@RepositoryRestResource(path = "risk-factors", collectionResourceRel = "risk-factors")
public interface RiskFactorRepository extends JpaRepository<RiskFactor, UUID> {

    RiskFactor findByName(String name);
}
