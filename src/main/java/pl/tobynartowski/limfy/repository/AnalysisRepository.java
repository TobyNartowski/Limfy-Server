package pl.tobynartowski.limfy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tobynartowski.limfy.model.persitent.Analysis;
import pl.tobynartowski.limfy.model.persitent.Disease;

import java.util.UUID;

@RepositoryRestResource
public interface AnalysisRepository extends JpaRepository<Analysis, UUID> {

    Analysis findByDisease(Disease disease);
}
