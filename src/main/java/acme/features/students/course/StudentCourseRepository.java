package acme.features.students.course;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.individual.lectures.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentCourseRepository extends AbstractRepository {

    @Query("select student from Student student where student.id = :activeRoleId")
    Student findOneStudentById(int activeRoleId);

    @Query("select enrolment.course from Enrolment enrolment where enrolment.student.id = :studentId and enrolment.draftMode is false and enrolment.course.draftMode is false")
    Set<Course> findAllMyEnrolledCourses(int studentId);

    @Query("select course from Course course where course.id = :masterId and course.draftMode is false")
    Course findOnePublishCourseById(int masterId);

}
