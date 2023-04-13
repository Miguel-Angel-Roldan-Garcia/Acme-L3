package acme.features.students.lecturer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface StudentLecturerRepository extends AbstractRepository {

    @Query("SELECT course.lecturer from Course course where course.id = :courseId")
    Lecturer findOneLecturerByCourseId(int courseId);

}
