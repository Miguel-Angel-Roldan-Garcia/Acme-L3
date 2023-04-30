
package acme.features.authenticated.bulletin;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.group.Bulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedBulletinRepository extends AbstractRepository {

	@Query("select b from Bulletin b")
	Collection<Bulletin> findAllBulletins();

	@Query("select b from Bulletin b where b.id= :id")
	Bulletin findBulletinById(int id);

}
