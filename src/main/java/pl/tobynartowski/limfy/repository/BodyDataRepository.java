package pl.tobynartowski.limfy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tobynartowski.limfy.model.BodyData;

import java.util.UUID;

@RepositoryRestResource(path = "body_data", collectionResourceRel = "body_data")
public interface BodyDataRepository extends JpaRepository<BodyData, UUID> {
}
