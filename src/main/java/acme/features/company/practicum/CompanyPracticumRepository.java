/*
 * WorkerApplicationRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.individual.companies.Practicum;
import acme.entities.individual.companies.PracticumSession;
import acme.entities.individual.lectures.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumRepository extends AbstractRepository {

	@Query("select c from Company c where c.id = :id")
	Company findOneCompanyById(int id);

	@Query("select p from Practicum p where p.id = :id")
	Practicum findOnePracticumById(int id);

	@Query("select p from Practicum p where p.code = :code")
	Practicum findOnePracticumByCode(String code);

	@Query("select p from Practicum p where p.company.id = :id")
	Collection<Practicum> findManyPracticumByCompanyId(int id);

	@Query("select c from Course c where c.code = :code")
	Course findOneCourseByCode(String code);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select ps from PracticumSession ps where ps.practicum.id = :id")
	Collection<PracticumSession> findManyPracticumSessionsByPracticumId(int id);

	@Query("select p.course.code from Practicum p where p.id = :id")
	String findCourseCodeByPracticumId(int id);

	@Query("SELECT course from Course course where course.draftMode = false")
	Collection<Course> findManyPublishedCourses();

}
