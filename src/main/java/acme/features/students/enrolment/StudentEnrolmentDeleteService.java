package acme.features.students.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.lectures.Course;
import acme.entities.individual.students.Activity;
import acme.entities.individual.students.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentDeleteService extends AbstractService<Student, Enrolment> {

    @Autowired
    protected StudentEnrolmentRepository repository;

    @Override
    public void check() {
	boolean status;

	status = super.getRequest().hasData("id", int.class);

	super.getResponse().setChecked(status);
    }

    @Override
    public void authorise() {
	boolean status;
	int masterId;
	Enrolment enrolment;
	Student student;

	masterId = super.getRequest().getData("id", int.class);
	enrolment = this.repository.findOneEnrolmentById(masterId);
	student = enrolment == null ? null : enrolment.getStudent();
	status = enrolment != null && enrolment.isDraftMode() && super.getRequest().getPrincipal().hasRole(student);

	super.getResponse().setAuthorised(status);
    }

    @Override
    public void load() {
	Enrolment object;
	int id;

	id = super.getRequest().getData("id", int.class);
	object = this.repository.findOneEnrolmentById(id);

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
    }

    @Override
    public void perform(final Enrolment object) {
	assert object != null;

	Collection<Activity> activities;

	activities = this.repository.findManyActivitiesByEnrolmentId(object.getId());
	this.repository.deleteAll(activities);
	this.repository.delete(object);
    }

    @Override
    public void unbind(final Enrolment object) {
	assert object != null;

	Collection<Course> courses;
	SelectChoices choices;
	Tuple tuple;

	courses = this.repository.findManyPublishedCourses();
	choices = SelectChoices.from(courses, "title", object.getCourse());

	tuple = super.unbind(object, "code", "motivation", "goals", "draftMode");
	tuple.put("course", choices.getSelected().getKey());
	tuple.put("courses", choices);

	super.getResponse().setData(tuple);
    }

}
