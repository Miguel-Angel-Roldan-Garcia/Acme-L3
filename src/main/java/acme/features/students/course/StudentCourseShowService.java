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
	Student student;
	int studentId;
	studentId = super.getRequest().getPrincipal().getActiveRoleId();
	student = this.repository.findOneStudentById(studentId);
	status = super.getRequest().getPrincipal().hasRole(student);

	masterId = super.getRequest().getData("id", int.class);
	course = this.repository.findOnePublishCourseById(masterId);
	status = course != null && super.getRequest().getPrincipal().hasRole(student)
		&& this.repository.findAllMyEnrolledCourses(studentId).contains(course);

	super.getResponse().setAuthorised(status);
    }

    @Override
    public void load() {
	Course object;
	int id;

	id = super.getRequest().getData("id", int.class);
	object = this.repository.findOnePublishCourseById(id);
	super.getBuffer().setData("course", object);
    }

    @Override
    public void unbind(final Course object) {
	assert object != null;

	Tuple tuple;

	tuple = super.unbind(object, "code", "title", "abstract$", "retailPrice", "link", "draftMode");
	super.getResponse().setData(tuple);

    }

}