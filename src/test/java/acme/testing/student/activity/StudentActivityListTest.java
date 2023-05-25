package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.individual.students.Enrolment;
import acme.testing.TestHarness;

public class StudentActivityListTest extends TestHarness {

    // Internal state ---------------------------------------------------------

    @Autowired
    protected StudentActivityTestRepository repository;

    // Test methods -----------------------------------------------------------

    @ParameterizedTest
    @CsvFileSource(resources = "/student/activity/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
    public void test100Positive(final int activityRecordIndex, final int enrolmentRecordIndex, final String code,
	    final String title, final String link, final String nature) {

	super.signIn("student1", "student1");

	super.clickOnMenu("Student", "List my enrolments");
	super.checkListingExists();
	super.sortListing(0, "asc");

	super.checkColumnHasValue(enrolmentRecordIndex, 0, code);
	super.clickOnListingRecord(enrolmentRecordIndex);
	super.checkInputBoxHasValue("code", code);
	super.clickOnButton("Their activities");

	super.checkListingExists();
	super.checkColumnHasValue(activityRecordIndex, 0, title);
	super.checkColumnHasValue(activityRecordIndex, 1, link);
	super.checkColumnHasValue(activityRecordIndex, 2, nature);
	super.clickOnListingRecord(activityRecordIndex);
	super.checkFormExists();

	super.signOut();
    }

    @Test
    public void test200Negative() {
	// HINT: there's no negative test case for this listing, since it doesn't
	// HINT+ involve filling in any forms.
    }

    @Test
    public void test300Hacking() {
	// HINT: This test checks that only the principal can see the activities of his
	// finished enrolment
	// HINT+ and checks that only the principal can see the activities if the
	// enrolment is finished

	Collection<Enrolment> enrolments;
	Collection<Enrolment> notFinishedEnrolments;
	String param;

	enrolments = this.repository.findManyEnrolmentsByStudentUsernameAndDraftModeIsFalse("student1");
	for (final Enrolment enrolment : enrolments) {

	    param = String.format("masterId=%d", enrolment.getId());

	    super.checkLinkExists("Sign in");
	    super.request("/student/activity/list", param);
	    super.checkPanicExists();

	    super.signIn("administrator", "administrator");
	    super.request("/student/activity/list", param);
	    super.checkPanicExists();
	    super.signOut();

	    super.signIn("lecturer1", "lecturer1");
	    super.request("/student/activity/list", param);
	    super.checkPanicExists();
	    super.signOut();

	    super.signIn("company1", "company1");
	    super.request("/student/activity/list", param);
	    super.checkPanicExists();
	    super.signOut();

	    super.signIn("assistant1", "assistant1");
	    super.request("/student/activity/list", param);
	    super.checkPanicExists();
	    super.signOut();

	    super.signIn("auditor1", "auditor1");
	    super.request("/student/activity/list", param);
	    super.checkPanicExists();
	    super.signOut();

	    super.signIn("student2", "student2");
	    super.request("/student/activity/list", param);
	    super.checkPanicExists();
	    super.signOut();
	}
	notFinishedEnrolments = this.repository.findManyEnrolmentsByStudentUsernameAndDraftModeIsTrue("student1");
	super.signIn("student1", "student1");
	for (final Enrolment enrolment : notFinishedEnrolments) {
	    param = String.format("masterId=%d", enrolment.getId());
	    super.request("/student/activity/list", param);
	    super.checkPanicExists();
	}
    }

}
