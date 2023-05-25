package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.individual.students.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentShowTest extends TestHarness {

    @Autowired
    protected StudentEnrolmentTestRepository repository;

    @ParameterizedTest
    @CsvFileSource(resources = "/student/enrolment/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
    public void test100Positive(final int recordIndex, final String code, final String course, final String motivation,
	    final String goals, final String draft_mode, final String lower_nibble, final String holder_name) {

	super.signIn("student1", "student1");

	super.clickOnMenu("Student", "List my enrolments");
	super.sortListing(0, "asc");
	super.clickOnListingRecord(recordIndex);
	super.checkFormExists();

	super.checkInputBoxHasValue("code", code);
	super.checkInputBoxHasValue("course", course);
	super.checkInputBoxHasValue("motivation", motivation);
	super.checkInputBoxHasValue("goals", goals);
	if (draft_mode.equals("false")) {
	    super.checkInputBoxHasValue("holderName", holder_name);
	    super.checkInputBoxHasValue("lowerNibble", lower_nibble);
	}

	super.signOut();
    }

    @Test
    public void test200Negative() {
	// HINT: there aren't any negative tests for this feature because it's a listing
	// HINT+ that doesn't involve entering any data in any forms.
    }

    @Test
    public void test300Hacking() {
	// HINT: this test tries to show an enrolment by someone who is not the
	// principal.

	Collection<Enrolment> enrolments;
	String param;

	enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
	for (final Enrolment enrolment : enrolments)
	    if (enrolment.isDraftMode()) {
		param = String.format("id=%d", enrolment.getId());

		super.checkLinkExists("Sign in");
		super.request("/student/enrolment/show", param);
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/student/enrolment/show", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/student/enrolment/show", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/student/enrolment/show", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/student/enrolment/show", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/student/enrolment/show", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student2", "student2");
		super.request("/student/enrolment/show", param);
		super.checkPanicExists();
		super.signOut();

	    }
    }

}
