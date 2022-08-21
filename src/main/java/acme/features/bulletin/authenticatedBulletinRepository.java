package acme.features.authenticated.bulletin	;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.bulletin.Bulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface authenticatedBulletinRepository extends AbstractRepository {

	@Query("select b from Bulletin b where b.id = :id")
	Bulletin findOneBulletinById(int id);

	@Query("select b from Bulletin b where b.flag = true")
	Collection<Bulletin> findManyBulletin();

}
