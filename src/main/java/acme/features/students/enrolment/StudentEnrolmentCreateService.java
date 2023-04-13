package acme.features.students.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.lectures.Course;
import acme.entities.individual.students.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentCreateService extends AbstractService<Student, Enrolment> {

    @Autowired
    protected StudentEnrolmentRepository repository;

    @Override
    public void check() {
	super.getResponse().setChecked(true);
    }

    @Override
    public void authorise() {
	super.getResponse().setAuthorised(true);
    }

    @Override
    public void load() {
	final Enrolment object;
	Student student;

	student = this.repository.findOneStudentById(super.getRequest().getPrincipal().getActiveRoleId());
	object = new Enrolment();
	object.setDraftMode(true);
	object.setStudent(student);

	super.getBuffer().setData(object);
    }

    @Override
    public void bind(final Enrolment object) {
	assert object != null;

	int courseId;
	Course course;

	courseId = super.getRequest().getData("course", int.class);
	course = this.repository.findOneCourseById(courseId);

	super.bind(object, "code", "motivation", "goals", "lowerNibble", "holderName");
	object.setCourse(course);
    }

    @Override
    public void validate(final Enrolment object) {
	assert object != null;

	if (!super.getBuffer().getErrors().hasErrors("code")) {
	    Enrolment existing;

	    existing = this.repository.findOneEnrolmentByCode(object.getCode());
	    super.state(existing == null, "code", "student.enrolment.form.error.duplicated");
	}

	if (!super.getBuffer().getErrors().hasErrors("course")) {
	    final Course selectedCourse = object.getCourse();
	    super.state(!selectedCourse.isDraftMode(), "course", "student.enrolment.form.error.not-published");
	}
    }

    @Override
    public void perform(final Enrolment object) {
	assert object != null;

	this.repository.save(object);
    }

    @Override
    public void unbind(final Enrolment object) {
	assert object != null;

	Collection<Course> courses;
	SelectChoices choices;
	Tuple tuple;

	courses = this.repository.findManyPublishedCourses();
	choices = SelectChoices.from(courses, "title", object.getCourse());

	tuple = super.unbind(object, "code", "motivation", "goals", "draftMode", "lowerNibble", "holderName");
	tuple.put("course", choices.getSelected().getKey());
	tuple.put("courses", choices);

	super.getResponse().setData(tuple);
    }

}
