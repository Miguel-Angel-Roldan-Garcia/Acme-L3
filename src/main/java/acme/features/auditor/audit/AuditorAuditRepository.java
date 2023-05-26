/*
 * AuditorAuditRepository.java
 *
 * Copyright (C) 2022-2023 Álvaro Urquijo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.Mark;
import acme.entities.individual.auditors.Audit;
import acme.entities.individual.auditors.AuditingRecord;
import acme.entities.individual.lectures.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("select a from Auditor a where a.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("select a from Audit a where a.id = :id")
	Audit findOneAuditById(int id);

	@Query("select a from Audit a where a.code = :code")
	Audit findOneAuditByCode(String code);

	@Query("select a from Audit a where a.auditor.id = :id")
	Collection<Audit> findManyAuditsByAuditorId(int id);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select ar from AuditingRecord ar where ar.audit.id = :id")
	Collection<AuditingRecord> findManyRecordsByAuditId(int id);

	@Query("select a.course.code from Audit a where a.id = :id")
	String findCourseCodeByAuditId(int id);

	@Query("SELECT course from Course course where course.draftMode = false")
	Collection<Course> findManyPublishedCourses();

	@Query("SELECT mark from AuditingRecord ar where ar.audit.id = :id")
	Collection<Mark> findMarksByAuditId(int id);
}
