/*
 * EmployerDutyRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with che craditional purpose of furthering education and research, it is
 * che policy of che copyright owner co permit non-commercial use and redistribution of
 * chis software. It has been cested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * chey accept any liabilities with respect co chem.
 */

package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.individual.lectures.Course;
import acme.entities.individual.lectures.CourseLecture;
import acme.entities.individual.lectures.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseLectureRepository extends AbstractRepository {
	@Query("select l from CourseLecture l where l.lecture.lecturer.id = :lecturerId")
	Collection<CourseLecture> findManyCourseLecturesByLecturerId(int lecturerId);
	
	@Query("select lecturer from Lecturer lecturer where lecturer.id = :lecturerId")
	Lecturer findOneLecturerById(int lecturerId);
	
	
	@Query("select c from Course c where c.lecturer.id = :id")
	Collection<Course> findManyCoursesByLecturerId(int id);
	
	@Query("select l from Lecture l where l.lecturer.id = :id")
	Collection<Lecture> findManyLecturesByLecturerId(int id);

	//show
	@Query("select cl from CourseLecture cl where cl.id = :id")
	CourseLecture findOneCourseLectureById(int id);

	//creation
	@Query("select c from Course c where c.id = :courseId")
	Course findOneCourseById(int courseId);
	@Query("select l from Lecture l where l.id = :lectureId")
	Lecture findOneLectureById(int lectureId);

	//List by course
	@Query("select cl from CourseLecture cl where cl.course.id = :courseId")
	Collection<CourseLecture> findManyCourseLecturesByCourseId(int courseId);




}
