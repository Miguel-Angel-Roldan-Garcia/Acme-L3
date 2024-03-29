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
public class StudentEnrolmentShowService extends AbstractService<Student, Enrolment> {

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
	status = super.getRequest().getPrincipal().hasRole(student) || enrolment != null;

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
    public void unbind(final Enrolment object) {
	assert object != null;

	Collection<Course> courses;
	SelectChoices choices;
	Tuple tuple;
	double totalWorkingTime = 0.;

	courses = this.repository.findManyPublishedCourses();
	choices = SelectChoices.from(courses, "code", object.getCourse());
	if (!object.isDraftMode()) {
	    final Collection<Activity> activities = this.repository.findManyActivitiesByEnrolmentId(object.getId());
	    for (final Activity activity : activities)
		totalWorkingTime += activity.getTimePeriod();
	}

	tuple = super.unbind(object, "code", "motivation", "goals", "draftMode", "lowerNibble", "holderName");
	tuple.put("course", choices.getSelected().getKey());
	tuple.put("courses", choices);
	tuple.put("workTime", totalWorkingTime);

	super.getResponse().setData(tuple);
    }

}
