package acme.features.students.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.individual.lectures.Course;
import acme.entities.individual.students.Activity;
import acme.entities.individual.students.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

    @Query("select enrolment from Enrolment enrolment where enrolment.id = :id")
    Enrolment findOneEnrolmentById(int id);

    @Query("SELECT enrolment FROM Enrolment enrolment where enrolment.code = :code")
    Enrolment findOneEnrolmentByCode(String code);

    @Query("select enrolment from Enrolment enrolment where enrolment.student.id = :studentId")
    Collection<Enrolment> findManyEnrolmentByStudentId(int studentId);

    @Query("select student from Student student where student.id = :id")
    Student findOneStudentById(int id);

    @Query("SELECT course from Course course where course.id = :courseId")
    Course findOneCourseById(int courseId);

    @Query("SELECT course from Course course")
    Collection<Course> findAllCourses();

    @Query("SELECT course from Course course where course.draftMode = false")
    Collection<Course> findManyPublishedCourses();

    @Query("SELECT activity FROM Activity activity WHERE activity.enrolment.id = :id")
    Collection<Activity> findManyActivitiesByEnrolmentId(int id);

}
