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

package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.individual.lectures.CourseLecture;
import acme.entities.individual.lectures.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {
	@Query("select l from Lecture l where l.lecturer.id = :lecturerId")
	Collection<Lecture> findManyLecturesByLecturerId(int lecturerId);
	
	@Query("select lecturer from Lecturer lecturer where lecturer.id = :lecturerId")
	Lecturer findOneLecturerById(int lecturerId);
	@Query("select lecture from Lecture lecture where lecture.id = :id")
	Lecture findOneLectureById(int id);
	
	
	//List by course
	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :courseId")
	Collection<Lecture> findManyLecturesByCourseId(Integer courseId);
	//Delete relations when deleting a lecture
	@Query("select cl from CourseLecture cl where cl.lecture.id = :id")
	Collection<CourseLecture> findManyCourseLecturesByLectureId(int id);


}
