/*
 * AuthenticatedAuditRepository.java
 *
 * Copyright (C) 2022-2023 Álvaro Urquijo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.Mark;
import acme.entities.individual.auditors.Audit;
import acme.entities.individual.auditors.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAuditRepository extends AbstractRepository {

	@Query("select a from Audit a where a.id = :id")
	Audit findOneAuditById(int id);

	@Query("select a from Audit a where a.course.id = :courseId")
	Collection<Audit> findAllCourseAudits(int courseId);

	@Query("select ar from AuditingRecord ar where ar.audit.id = :id")
	Collection<AuditingRecord> findManyRecordByAuditId(int id);

	@Query("select a.course.code from Audit a where a.id = :id")
	String findCourseCodeByAuditId(int id);

	@Query("SELECT mark from AuditingRecord ar where ar.audit.id = :id")
	Collection<Mark> findMarksByAuditId(int id);

}
