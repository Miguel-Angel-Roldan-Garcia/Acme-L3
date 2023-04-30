
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

	courseId = super.getRequest().getData("course", int.class);
	course = this.repository.findOneCourseById(courseId);

	super.bind(object, "code", "motivation", "goals", "lowerNibble", "holderName");
	object.setCourse(course);
    }

    @Override
    public void validate(final Enrolment object) {
	assert object != null;
	// Es necesario inicializar estas variables para la validación de sus
	// condiciones
	String creditNumber = "";
	String cvc = "";
	String holderName = "";
	String expiryDate = "";
	boolean finishable = false;
	if (object.isDraftMode()) {
	    creditNumber = super.getRequest().getData("creditCardNumber", String.class);
	    cvc = super.getRequest().getData("cvc", String.class);
	    holderName = super.getRequest().getData("holderName", String.class);
	    expiryDate = super.getRequest().getData("expiryDate", String.class);
	    finishable = !creditNumber.isEmpty() || !cvc.isEmpty() || !holderName.isEmpty() || !expiryDate.isEmpty();
	}

	if (!super.getBuffer().getErrors().hasErrors("code")) {
	    Enrolment existing;
	    existing = this.repository.findOneEnrolmentByCode(object.getCode());
	    // da error, de alguna forma
	    super.state(existing == null || existing.equals(object), "code", "student.enrolment.form.error.duplicated");
	}

	if (!super.getBuffer().getErrors().hasErrors("course")) {
	    final Course selectedCourse = object.getCourse();
	    super.state(!selectedCourse.isDraftMode(), "course", "student.enrolment.form.error.not-published");
	}
	if (finishable) {
	    if (!super.getBuffer().getErrors().hasErrors("creditCardNumber"))
		if (creditNumber.matches("^[0-9]{16}$"))
		    super.state(this.isValidCheckSum(creditNumber), "creditCardNumber",
			    "student.enrolment.form.error.not-valid-creditCard-checksum");
		else
		    super.state(false, "creditCardNumber", "student.enrolment.form.error.not-valid-creditCard");
	    if (!super.getBuffer().getErrors().hasErrors("expiryDate")) {
		final boolean validFormat = expiryDate.matches("^\\d{2}/\\d{2}$");

		if (validFormat && Integer.parseInt(expiryDate.split("/")[1]) <= 12) {
		    final Date date = MomentHelper.parse("yy/MM", expiryDate);
		    final Date actualExpiryDate = new Date(date.getYear(), date.getMonth(),
			    this.getLastDayOfMonth(date.getMonth()), 23, 59);
		    super.state(date != null && MomentHelper.isAfter(actualExpiryDate, MomentHelper.getCurrentMoment()),
			    "expiryDate", "student.enrolment.form.error.past-expiry-date");
		} else
		    super.state(false, "expiryDate", "student.enrolment.form.error.not-valid-date-format");
	    }
	    if (!super.getBuffer().getErrors().hasErrors("cvc"))
		super.state(cvc.matches("^[0-9]{3}$"), "cvc", "student.enrolment.form.error.not-valid-cvc-pattern");
	    if (!super.getBuffer().getErrors().hasErrors("holderName"))
		super.state(!holderName.isEmpty() && holderName.length() < 25, "holderName",
			"student.enrolment.form.error.not-valid-holderName");

	}
    }

    @Override
    public void perform(final Enrolment object) {
	assert object != null;
	String creditNumber = "";
	String cvc = "";
	String holderName = "";
	String expiryDate = "";
	boolean finishable = false;
	if (object.isDraftMode()) {
	    creditNumber = super.getRequest().getData("creditCardNumber", String.class);
	    cvc = super.getRequest().getData("cvc", String.class);
	    holderName = super.getRequest().getData("holderName", String.class);
	    expiryDate = super.getRequest().getData("expiryDate", String.class);
	    finishable = !creditNumber.isEmpty() || !cvc.isEmpty() || !holderName.isEmpty() || !expiryDate.isEmpty();
	}
	if (finishable) {
	    object.setDraftMode(false);
	    object.setHolderName(holderName);
	    object.setLowerNibble(creditNumber.substring(creditNumber.length() - 4));
	}
	this.repository.save(object);
    }

    @Override
    public void unbind(final Enrolment object) {
	assert object != null;

	Collection<Course> courses;
	SelectChoices choices;
	Tuple tuple;
	String creditNumber = "";
	String cvc = "";
	String holderName = "";
	String expiryDate = "";
	boolean finishable = false;
	if (object.isDraftMode()) {
	    creditNumber = super.getRequest().getData("creditCardNumber", String.class);
	    cvc = super.getRequest().getData("cvc", String.class);
	    holderName = super.getRequest().getData("holderName", String.class);
	    expiryDate = super.getRequest().getData("expiryDate", String.class);
	    finishable = !creditNumber.isEmpty() || !cvc.isEmpty() || !holderName.isEmpty() || !expiryDate.isEmpty();
	}

	courses = this.repository.findManyPublishedCourses();
	choices = SelectChoices.from(courses, "code", object.getCourse());

	tuple = super.unbind(object, "code", "motivation", "goals", "draftMode", "lowerNibble", "holderName");
	tuple.put("course", choices.getSelected().getKey());
	tuple.put("courses", choices);
	if (finishable) {
	    if (creditNumber.matches("^[0-9]{16}$") && this.isValidCheckSum(creditNumber))
		tuple.put("creditCardNumber", creditNumber);
	    if (cvc.matches("^[0-9]{3}$"))
		tuple.put("cvc", cvc);
	    if (expiryDate.matches("^\\d{2}/\\d{2}$") && Integer.parseInt(expiryDate.split("/")[1]) <= 12) {
		final Date date = MomentHelper.parse("yy/MM", expiryDate);
		final Date actualExpiryDate = new Date(date.getYear(), date.getMonth(),
			this.getLastDayOfMonth(date.getMonth()), 23, 59);
		if (date != null && MomentHelper.isAfter(actualExpiryDate, MomentHelper.getCurrentMoment()))
		    tuple.put("expiryDate", expiryDate);
	    }
	    if (!holderName.isEmpty() && holderName.length() < 25)
		tuple.put("holderName", holderName);

	}

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

    // VALID NUMBER = 2600000000000000
    private boolean isValidCheckSum(final String creditNumber) {
	int sum = 0;
	for (int i = 0; i < creditNumber.length() - 1; i++) {
	    final char number = creditNumber.charAt(i);
	    int value = 0;
	    if ((i + 2) % 2 == 0)
		value = Character.getNumericValue(number) * 2;
	    else
		value = Character.getNumericValue(number);
	    if (value >= 10) {
		final String newNumber = Integer.toString(value);
		sum += Character.getNumericValue(newNumber.charAt(0)) + Character.getNumericValue(newNumber.charAt(1));
	    } else
		sum += value;
	}
	return sum % 10 == 0;
    }

}
