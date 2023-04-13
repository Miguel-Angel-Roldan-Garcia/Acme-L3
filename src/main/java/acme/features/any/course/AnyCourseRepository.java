package acme.features.any.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.individual.lectures.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyCourseRepository extends AbstractRepository {

    @Query("SELECT course from Course course where course.id = :courseId")
    Course findOneCourseById(int courseId);

    @Query("SELECT course FROM Course course WHERE course.draftMode IS FALSE")
    Collection<Course> findAllPublishedCourses();

}
