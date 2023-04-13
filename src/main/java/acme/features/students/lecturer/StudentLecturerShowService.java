package acme.features.students.lecturer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;
import acme.roles.Student;

@Service
public class StudentLecturerShowService extends AbstractService<Student, Lecturer> {

    @Autowired
    protected StudentLecturerRepository repository;

    @Override
    public void check() {
	boolean status;

	status = super.getRequest().hasData("masterId", int.class);

	super.getResponse().setChecked(status);
    }

    // SI HAY QUE CAMBIAR LA FORMA DEL LISTADO, EL AUTHORISE HAY QUE MODIFICARLO
    @Override
    public void authorise() {
	boolean status;
	int masterId;
	Lecturer lecturer;

	masterId = super.getRequest().getData("masterId", int.class);
	lecturer = this.repository.findOneLecturerByCourseId(masterId);
	status = lecturer != null;

	super.getResponse().setAuthorised(status);
    }

    @Override
    public void load() {
	Lecturer object;
	int id;

	id = super.getRequest().getData("masterId", int.class);
	object = this.repository.findOneLecturerByCourseId(id);
	super.getBuffer().setData(object);
    }

    @Override
    public void unbind(final Lecturer object) {
	assert object != null;

	Tuple tuple;

	tuple = super.unbind(object, "almaMater", "resume", "qualifications", "link");
	super.getResponse().setData(tuple);

    }

}
