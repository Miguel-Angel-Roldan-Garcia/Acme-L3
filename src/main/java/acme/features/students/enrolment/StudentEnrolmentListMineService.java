package acme.features.students.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.students.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentListMineService extends AbstractService<Student, Enrolment> {

    @Autowired
    protected StudentEnrolmentRepository repository;

    @Override
    public void check() {
	super.getResponse().setChecked(true);
    }

    @Override
    public void authorise() {
	boolean state;
	final int studentId = super.getRequest().getPrincipal().getActiveRoleId();
	final Student student = this.repository.findOneStudentById(studentId);
	state = super.getRequest().getPrincipal().hasRole(student);
	super.getResponse().setAuthorised(state);
    }

    @Override
    public void load() {
	Collection<Enrolment> objects;
	final int studentId = super.getRequest().getPrincipal().getActiveRoleId();
	objects = this.repository.findManyEnrolmentByStudentId(studentId);
	super.getBuffer().setData(objects);
    }

    @Override
    public void unbind(final Enrolment object) {
	assert object != null;

	Tuple tuple;

	tuple = super.unbind(object, "code", "motivation", "goals");

	super.getResponse().setData(tuple);
    }

}
