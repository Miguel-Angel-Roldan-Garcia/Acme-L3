/*
 * CompanyDashboardRepository.java
 *
 * Copyright (C) 2022-2023 Javier Fernández Castillo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.company.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.individual.companies.Practicum;
import acme.entities.individual.companies.PracticumSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyDashboardRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.company.id = :id")
	Collection<Practicum> findAllPracticumByCompanyId(int id);

	@Query("select ps from PracticumSession ps where ps.practicum.id = :id")
	Collection<PracticumSession> findAllPracticumSessionsByPracticumId(int id);

}
