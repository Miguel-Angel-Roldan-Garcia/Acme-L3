package acme.features.students.enrolments;

import org.springframework.stereotype.Service;

import acme.entities.individual.students.Enrolment;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentShowService extends AbstractService<Student, Enrolment> {

}
