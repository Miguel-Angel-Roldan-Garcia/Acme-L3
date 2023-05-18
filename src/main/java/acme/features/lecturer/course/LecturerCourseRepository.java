
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
	@Query("select sc.currency from SystemConfiguration sc")
	Collection<String> findAllCurrencySystemConfiguration();



}
