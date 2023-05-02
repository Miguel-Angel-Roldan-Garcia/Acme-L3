/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.Nature;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {


	@Query("select count(*) from Lecture lecture where (lecture.nature = :nature and lecture.lecturer.id = :lecturerId)")
	Integer lecturesCountByNatureOfLecturer(Nature nature, int lecturerId);

	@Query("select avg(l.estimatedLearningTime) from CourseLecture cl JOIN cl.lecture l WHERE cl.course.lecturer.id = :lecturerId")
	Double averageEltInCoursesOfLecturer(int lecturerId);
	
	@Query("select stddev(l.estimatedLearningTime) from CourseLecture cl JOIN cl.lecture l WHERE cl.course.lecturer.id = :lecturerId")
	Double deviationEltInCoursesOfLecturer(int lecturerId);
	
	@Query("select max(l.estimatedLearningTime) from CourseLecture cl JOIN cl.lecture l WHERE cl.course.lecturer.id = :lecturerId")
	Double maxEltInCoursesOfLecturer(int lecturerId);
	
	@Query("select min(l.estimatedLearningTime) from CourseLecture cl JOIN cl.lecture l WHERE cl.course.lecturer.id = :lecturerId")
	Double minEltInCoursesOfLecturer(int lecturerId);

	
	@Query("select avg(l.estimatedLearningTime) from Lecture l WHERE l.lecturer.id = :lecturerId")
	Double averageEltInLecturesOfLecturer(int lecturerId);
	
	@Query("select stddev(l.estimatedLearningTime) from Lecture l WHERE l.lecturer.id = :lecturerId")
	Double deviationEltInLecturesOfLecturer(int lecturerId);
	
	@Query("select max(l.estimatedLearningTime) from Lecture l WHERE l.lecturer.id = :lecturerId")
	Double maxEltInLecturesOfLecturer(int lecturerId);
	
	@Query("select min(l.estimatedLearningTime) from Lecture l WHERE l.lecturer.id = :lecturerId")
	Double minEltInLecturesOfLecturer(int lecturerId);
}
