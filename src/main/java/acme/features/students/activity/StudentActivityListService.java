package acme.features.students.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.students.Activity;
import acme.entities.individual.students.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityListService extends AbstractService<Student, Activity> {

    @Autowired
    protected StudentActivityRepository repository;

    @Override
    public void check() {
	boolean status;

	status = super.getRequest().hasData("masterId", int.class);

	super.getResponse().setChecked(status);
    }

    @Override
    public void authorise() {
	boolean status;
	int masterId;
	Enrolment enrolment;

	masterId = super.getRequest().getData("masterId", int.class);
	enrolment = this.repository.findOneEnrolmentById(masterId);
	status = enrolment != null && !enrolment.isDraftMode()
		&& super.getRequest().getPrincipal().hasRole(enrolment.getStudent());

	super.getResponse().setAuthorised(status);
    }

    @Override
    public void load() {
	Collection<Activity> objects;
	int masterId;

	masterId = super.getRequest().getData("masterId", int.class);
	objects = this.repository.findManyActivitiesByEnrolmentId(masterId);

	super.getBuffer().setData(objects);
    }

    @Override
    public void unbind(final Activity object) {
	assert object != null;

	Tuple tuple;

	tuple = super.unbind(object, "title", "link", "nature");

	super.getResponse().setData(tuple);
    }

    @Override
    public void unbind(final Collection<Activity> objects) {
	assert objects != null;

	int masterId;
	Enrolment enrolment;
	final boolean showCreate;

	masterId = super.getRequest().getData("masterId", int.class);
	enrolment = this.repository.findOneEnrolmentById(masterId);
	showCreate = !enrolment.isDraftMode() && super.getRequest().getPrincipal().hasRole(enrolment.getStudent());

	super.getResponse().setGlobal("masterId", masterId);
	super.getResponse().setGlobal("showCreate", showCreate);
    }

}
