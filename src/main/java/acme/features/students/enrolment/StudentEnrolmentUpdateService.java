
package acme.features.students.enrolment;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.lectures.Course;
import acme.entities.individual.students.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentUpdateService extends AbstractService<Student, Enrolment> {

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
	boolean toFinalise;

	courseId = super.getRequest().getData("course", int.class);
	course = this.repository.findOneCourseById(courseId);
	toFinalise = super.getRequest().getData("confirmed", boolean.class);
	if (toFinalise)
	    super.bind(object, "code", "motivation", "goals", "lowerNibble", "creditCardNumber", "cvc", "expiryDate",
		    "holderName");
	else
	    super.bind(object, "code", "motivation", "goals");
	object.setCourse(course);
    }

    // VALID SPANISH NUMBER = 2600000000000000
    // VALID ENGLISH NUMBER = 26000000000000
    @Override
    public void validate(final Enrolment object) {
	assert object != null;

	boolean finishable = false;
	if (object.isDraftMode())
	    finishable = super.getRequest().getData("confirmed", boolean.class);

	if (!super.getBuffer().getErrors().hasErrors("code")) {
	    Enrolment existing;
	    existing = this.repository.findOneEnrolmentByCode(object.getCode());
	    super.state(existing == null || existing.equals(object), "code", "student.enrolment.form.error.duplicated");
	}

	if (!super.getBuffer().getErrors().hasErrors("course")) {
	    final Course selectedCourse = object.getCourse();
	    super.state(!selectedCourse.isDraftMode(), "course", "student.enrolment.form.error.not-published");
	}
	if (finishable) {
	    final String language = super.getRequest().getLocale().getDisplayLanguage();
	    if (!super.getBuffer().getErrors().hasErrors("expiryDate")) {

		final String expiryDate = object.getExpiryDate();
		String dateFormat = "";
		if (language.equals("Spanish") && Integer.parseInt(expiryDate.split("/")[1]) <= 12)
		    dateFormat = "yy/MM";
		else if (language.equals("English") && Integer.parseInt(expiryDate.split("/")[0]) <= 12)
		    dateFormat = "MM/yy";
		else
		    super.state(false, "expiryDate", "student.enrolment.form.error.not-valid-date-format");
		if (!dateFormat.isEmpty()) {
		    final Date date = MomentHelper.parse(dateFormat, expiryDate);
		    final Date actualExpiryDate = new Date(date.getYear(), date.getMonth(),
			    this.getLastDayOfMonth(date.getMonth()), 23, 59);
		    super.state(date != null && MomentHelper.isAfter(actualExpiryDate, MomentHelper.getCurrentMoment()),
			    "expiryDate", "student.enrolment.form.error.past-expiry-date");
		}

	    }
	    if (!super.getBuffer().getErrors().hasErrors("creditCardNumber"))
		super.state(
			language.equals("Spanish") && object.getCreditCardNumber().length() == 16
				|| language.equals("English") && object.getCreditCardNumber().length() == 14,
			"creditCardNumber", "student.enrolment.form.error.not-valid-credit-number-size");
	    if (!super.getBuffer().getErrors().hasErrors("holderName"))
		super.state(!object.getHolderName().isEmpty(), "holderName",
			"student.enrolment.form.error.not-valid-holderName");

	}
    }

    @Override
    public void perform(final Enrolment object) {
	assert object != null;
	boolean finishable = false;
	if (object.isDraftMode())
	    finishable = super.getRequest().getData("confirmed", boolean.class);
	if (finishable) {
	    object.setDraftMode(false);
	    object.setLowerNibble(object.getCreditCardNumber().substring(object.getCreditCardNumber().length() - 4));
	}
	this.repository.save(object);
    }

    @Override
    public void unbind(final Enrolment object) {
	assert object != null;

	Collection<Course> courses;
	SelectChoices choices;
	Tuple tuple;
	final boolean toFinalise = super.getRequest().getData("confirmed", boolean.class);

	courses = this.repository.findManyPublishedCourses();
	choices = SelectChoices.from(courses, "code", object.getCourse());

	if (toFinalise)
	    tuple = super.unbind(object, "code", "motivation", "goals", "lowerNibble", "creditCardNumber", "cvc",
		    "expiryDate", "holderName", "draftMode");
	else
	    tuple = super.unbind(object, "code", "motivation", "goals", "draftMode");
	tuple.put("confirmed", toFinalise);
	tuple.put("course", choices.getSelected().getKey());
	tuple.put("courses", choices);

	super.getResponse().setData(tuple);
    }

    // ----------AUXILIAR METHOT-------------
    private int getLastDayOfMonth(final int month) {
	if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
	    return 31;
	else if (month == 4 || month == 6 || month == 9 || month == 11)
	    return 30;
	else
	    return 29;
    }

}
