package acme.features.students.course;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.lectures.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentCourseListService extends AbstractService<Student, Course> {

    @Autowired
    protected StudentCourseRepository repository;

    @Override
    public void check() {
	super.getResponse().setChecked(true);
    }

    @Override
    public void authorise() {
	boolean status;
	Student student;

	student = this.repository.findOneStudentById(super.getRequest().getPrincipal().getActiveRoleId());
	status = super.getRequest().getPrincipal().hasRole(student);
	super.getResponse().setAuthorised(status);
    }

    @Override
    public void load() {
	Set<Course> objects;
	final int studentId = super.getRequest().getPrincipal().getActiveRoleId();

	objects = this.repository.findAllMyEnrolledCourses(studentId);

	super.getBuffer().setData(objects);
    }

    @Override
    public void unbind(final Course object) {
	assert object != null;

	Tuple tuple;

	tuple = super.unbind(object, "code", "title", "abstract$", "retailPrice", "link");

	super.getResponse().setData(tuple);
    }

}