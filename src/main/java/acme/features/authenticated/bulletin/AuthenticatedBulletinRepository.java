/*
 * AuthenticatedBulletinRepository.java
 *
 * Copyright (C) 2022-2023 Álvaro Urquijo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

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
