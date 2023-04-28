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

package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.individual.lectures.Course;
import acme.entities.individual.lectures.CourseLecture;
import acme.entities.individual.lectures.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select a from Lecturer a where a.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select t from Course t where t.id = :id")
	Course findOneCourseById(int id);


	//Publish auth, show and update(calculate nature) 
	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :id")
	Collection<Lecture> findManyLecturesByCourseId(int id);
	
	//Create
	@Query("select sc.currency from SystemConfiguration sc")
	String findCurrentSystemCurrency();
	
	//Publish
	@Query("select c from Course c where c.code = :code")
	Course findOneCourseByCode(String code);
	
	//List
	@Query("select t from Course t where t.lecturer.id = :id")
	Collection<Course> findManyCoursesByLecturerId(int id);
	
	//Delete
	@Query("select cl from CourseLecture cl where cl.course.id = :id")
	Collection<CourseLecture> findManyCourseLecturesByCourseId(int id);



}
