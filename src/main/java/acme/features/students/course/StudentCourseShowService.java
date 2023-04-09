package acme.features.students.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.lectures.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentCourseShowService extends AbstractService<Student, Course> {

    @Autowired
    protected StudentCourseRepository repository;

    @Override
    public void check() {
	boolean status;

	status = super.getRequest().hasData("id", int.class);

	super.getResponse().setChecked(status);
    }

    // SI HAY QUE CAMBIAR LA FORMA DEL LISTADO, EL AUTHORISE HAY QUE MODIFICARLO
    @Override
    public void authorise() {
	boolean status;
	int masterId;
	Course course;

	masterId = super.getRequest().getData("id", int.class);
	course = this.repository.findOneCourseById(masterId);
	status = course != null;

	super.getResponse().setAuthorised(status);
    }

    @Override
    public void load() {
	Course object;
	int id;

	id = super.getRequest().getData("id", int.class);
	object = this.repository.findOneCourseById(id);
	super.getBuffer().setData("course", object);
    }

    @Override
    public void unbind(final Course object) {
	assert object != null;

	Tuple tuple;

	tuple = super.unbind(object, "code", "title", "abstract$", "retailPrice", "link", "draftMode");
	tuple.put("lecturer", object.getLecturer().getId());
	super.getResponse().setData(tuple);

    }

}
