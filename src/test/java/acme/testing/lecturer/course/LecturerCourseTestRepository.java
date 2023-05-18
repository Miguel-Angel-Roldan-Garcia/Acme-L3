
package acme.testing.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.individual.lectures.Course;
import acme.framework.repositories.AbstractRepository;

public interface LecturerCourseTestRepository extends AbstractRepository {

	@Query("select c from Course c where c.lecturer.userAccount.username = :username")
	Collection<Course> findManyCoursesByLecturerUsername(String username);



}
