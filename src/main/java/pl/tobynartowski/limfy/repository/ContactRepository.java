package pl.tobynartowski.limfy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tobynartowski.limfy.model.persitent.Contact;

import java.util.UUID;

@RepositoryRestResource
public interface ContactRepository extends JpaRepository<Contact, UUID> {
}
