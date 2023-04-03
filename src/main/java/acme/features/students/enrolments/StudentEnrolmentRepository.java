package acme.features.students.enrolments;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.individual.students.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

    @Query("select enrolment from Enrolment enrolment where enrolment.student.id = :studentId")
    Collection<Enrolment> findManyEnrolmentByStudentId(int studentId);

}
