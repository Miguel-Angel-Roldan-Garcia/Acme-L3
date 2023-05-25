package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.individual.students.Activity;
import acme.testing.TestHarness;

public class StudentActivityShowTest extends TestHarness {

    // Internal state ---------------------------------------------------------

    @Autowired
    protected StudentActivityTestRepository repository;

    // Test methods -----------------------------------------------------------

    @ParameterizedTest
    @CsvFileSource(resources = "/student/activity/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
    public void test100Positive(final int activityRecordIndex, final int enrolmentRecordIndex, final String code,
	    final String title, final String abstract$, final String finish_date, final String initial_date,
	    final String link, final String nature) {

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

	super.checkInputBoxHasValue("title", title);
	super.checkInputBoxHasValue("abstract$", abstract$);
	super.checkInputBoxHasValue("initialDate", initial_date);
	super.checkInputBoxHasValue("finishDate", finish_date);
	super.checkInputBoxHasValue("link", link);
	super.checkInputBoxHasValue("nature", nature);
	super.signOut();
    }

    @Test
    public void test200Negative() {
	// HINT: there's no negative test case for this listing, since it doesn't
	// HINT+ involve filling in any forms.
    }

    @Test
    public void test300Hacking() {

	Collection<Activity> activities;
	String param;

	activities = this.repository.findManyActivitiesByStudentUsername("student1");
	for (final Activity activity : activities)
	    if (activity.getEnrolment().isDraftMode()) {
		param = String.format("id=%d", activity.getEnrolment().getId());

		super.checkLinkExists("Sign in");
		super.request("/student/activity/show", param);
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/student/activity/show", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/student/activity/show", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/student/activity/show", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/student/activity/show", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/student/activity/show", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student2", "student2");
		super.request("/student/activity/show", param);
		super.checkPanicExists();
		super.signOut();
	    }
    }

}
