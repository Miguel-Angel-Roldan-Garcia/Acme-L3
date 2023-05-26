package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.individual.students.Enrolment;
import acme.testing.TestHarness;

public class StudentActivityDeleteTest extends TestHarness {

    @Autowired
    protected StudentActivityTestRepository repository;

    // Test methods -----------------------------------------------------------

    @ParameterizedTest
    @CsvFileSource(resources = "/student/activity/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
    public void test100Positive(final int activityRecordIndex, final int enrolmentRecordIndex, final String code,
	    final String title, final String abstract$, final String finish_date, final String initial_date,
	    final String link, final String nature) {

	super.signIn("student1", "student1");

	super.clickOnMenu("Student", "List my enrolments");
	super.checkListingExists();
	super.sortListing(0, "asc");

	super.clickOnListingRecord(enrolmentRecordIndex);
	super.checkInputBoxHasValue("code", code);
	super.clickOnButton("Their activities");

	super.sortListing(0, "asc");
	super.clickOnListingRecord(activityRecordIndex);
	super.checkFormExists();
	super.checkInputBoxHasValue("title", title);
	super.checkInputBoxHasValue("abstract$", abstract$);
	super.checkInputBoxHasValue("finishDate", finish_date);
	super.checkInputBoxHasValue("initialDate", initial_date);
	super.checkInputBoxHasValue("link", link);
	super.checkInputBoxHasValue("nature", nature);
	super.clickOnSubmit("Delete");
	super.checkNotErrorsExist();
	super.signOut();
    }

    @Test
    public void test200Negative() {
	// HINT: there aren't any negative tests for this feature because it's a listing
	// HINT+ that doesn't involve entering any data in any forms.

    }

    @Test
    public void test300Hacking() {
	// HINT: this test tries to delete an activity for an enrolment as a principal
	// without
	// HINT: the "Student" role.

	Collection<Enrolment> enrolments;
	String param;

	enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
	for (final Enrolment enrolment : enrolments) {
	    param = String.format("masterId=%d", enrolment.getId());

	    super.checkLinkExists("Sign in");
	    super.request("/student/activity/delete", param);
	    super.checkPanicExists();

	    super.signIn("administrator", "administrator");
	    super.request("/student/activity/delete", param);
	    super.checkPanicExists();
	    super.signOut();

	    super.signIn("lecturer1", "lecturer1");
	    super.request("/student/activity/delete", param);
	    super.checkPanicExists();
	    super.signOut();

	    super.signIn("company1", "company1");
	    super.request("/student/activity/delete", param);
	    super.checkPanicExists();
	    super.signOut();

	    super.signIn("assistant1", "assistant1");
	    super.request("/student/activity/delete", param);
	    super.checkPanicExists();
	    super.signOut();

	    super.signIn("auditor1", "auditor1");
	    super.request("/student/activity/delete", param);
	    super.checkPanicExists();
	    super.signOut();
	}
    }

    @Test
    public void test301Hacking() {
	// HINT: this test tries to delete an activity for a not finished enrolment
	// created by
	// HINT+ the principal.

	Collection<Enrolment> enrolments;
	String param;

	super.checkLinkExists("Sign in");
	super.signIn("student1", "student1");
	enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
	for (final Enrolment enrolment : enrolments)
	    if (enrolment.isDraftMode()) {
		param = String.format("masterId=%d", enrolment.getId());
		super.request("/student/activity/delete", param);
		super.checkPanicExists();
	    }
    }

    @Test
    public void test302Hacking() {
	// HINT: this test tries to delete activities for enrolments that weren't
	// created
	// HINT+ by the principal.

	Collection<Enrolment> enrolments;
	String param;

	super.checkLinkExists("Sign in");
	super.signIn("student2", "student2");
	enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
	for (final Enrolment enrolment : enrolments) {
	    param = String.format("masterId=%d", enrolment.getId());
	    super.request("/student/activity/update", param);
	    super.checkPanicExists();
	}
    }

}
