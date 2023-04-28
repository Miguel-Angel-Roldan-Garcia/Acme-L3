/*
 * EmployerDutyRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.individual.companies.Practicum;
import acme.entities.individual.companies.PracticumSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyPracticumSessionRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.id = :id")
	Practicum findOnePracticumById(int id);

	@Query("select ps.practicum from PracticumSession ps where ps.id = :id")
	Practicum findOnePracticumByPracticumSessionId(int id);

	@Query("select ps from PracticumSession ps where ps.id = :id")
	PracticumSession findOnePracticumSessionById(int id);

	@Query("select ps from PracticumSession ps where ps.practicum.id = :masterId")
	Collection<PracticumSession> findManyPracticumSessionsByMasterId(int masterId);

}
