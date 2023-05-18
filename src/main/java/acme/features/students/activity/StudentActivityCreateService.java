package acme.features.students.activity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.individual.students.Activity;
import acme.entities.individual.students.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

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
	Activity object;
	int masterId;
	Enrolment enrolment;

	masterId = super.getRequest().getData("masterId", int.class);
	enrolment = this.repository.findOneEnrolmentById(masterId);

	object = new Activity();
	object.setEnrolment(enrolment);
//	object.setInitialDate(MomentHelper.getCurrentMoment());
//	object.setFinishDate(MomentHelper.getCurrentMoment());

	super.getBuffer().setData(object);
    }

    @Override
    public void bind(final Activity object) {
	assert object != null;

	super.bind(object, "title", "abstract$", "initialDate", "finishDate", "link", "nature");
    }

    @Override
    public void validate(final Activity object) {
	assert object != null;
	final Date maxDate = MomentHelper.parse("yyyy/MM/dd HH:mm", "2100/12/31 23:59");
	final Date minDate = MomentHelper.parse("yyyy/MM/dd HH:mm", "2000/01/01 00:01");
	if (!super.getBuffer().getErrors().hasErrors("initialDate")) {
	    final Date initialDate = object.getInitialDate();
	    super.state(MomentHelper.isBefore(initialDate, maxDate) && MomentHelper.isAfter(initialDate, minDate),
		    "initialDate", "student.activity.form.error.not-valid-initial-date");
	}
	if (!super.getBuffer().getErrors().hasErrors("finishDate")) {
	    final Date finishDate = object.getFinishDate();
	    super.state(MomentHelper.isBefore(finishDate, maxDate) && MomentHelper.isAfter(finishDate, minDate),
		    "finishDate", "student.activity.form.error.not-valid-finish-date");
	}

	if (!super.getBuffer().getErrors().hasErrors("initialDate")
		&& !super.getBuffer().getErrors().hasErrors("finishDate")) {
	    final Date initialDate = object.getInitialDate();
	    final Date finishDate = object.getFinishDate();
	    super.state(MomentHelper.isBefore(initialDate, finishDate) && MomentHelper.isAfter(finishDate, initialDate),
		    "*", "student.activity.form.error.not-valid-dates");
	}
    }

    @Override
    public void perform(final Activity object) {
	assert object != null;

	this.repository.save(object);
    }

    @Override
    public void unbind(final Activity object) {
	assert object != null;

	Tuple tuple;
	SelectChoices choices;

	choices = SelectChoices.from(Nature.class, object.getNature());
	tuple = super.unbind(object, "title", "abstract$", "initialDate", "finishDate", "link", "nature");
	tuple.put("masterId", super.getRequest().getData("masterId", int.class));
	tuple.put("nature", choices.getSelected().getKey());
	tuple.put("natures", choices);
	super.getResponse().setData(tuple);
    }

}
