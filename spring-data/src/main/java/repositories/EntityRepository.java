package repositories;

import entities.MyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Kamill Sokol
 */
@RepositoryRestResource
public interface EntityRepository extends CrudRepository<MyEntity, Long> {
}
