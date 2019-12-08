package pl.tobynartowski.limfy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tobynartowski.limfy.model.persitent.BodyData;

import java.util.UUID;

@RepositoryRestResource(path = "body-data", collectionResourceRel = "body-data")
public interface BodyDataRepository extends JpaRepository<BodyData, UUID> {
}
