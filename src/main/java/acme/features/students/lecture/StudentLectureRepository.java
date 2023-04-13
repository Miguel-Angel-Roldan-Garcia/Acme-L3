package acme.features.students.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.individual.lectures.Course;
import acme.entities.individual.lectures.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentLectureRepository extends AbstractRepository {

    @Query("SELECT course from Course course where course.id = :courseId")
    Course findOneCourseById(int courseId);

    @Query("SELECT courseLecture.lecture FROM CourseLecture courseLecture WHERE courseLecture.course.id = :id")
    Collection<Lecture> findManyLecturesByCourseId(int id);

    @Query("select student from Student student where student.id = :id")
    Student findOneStudentById(int id);

}
