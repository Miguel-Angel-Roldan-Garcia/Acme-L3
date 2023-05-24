package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.individual.students.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentUpdateTest extends TestHarness {

    @Autowired
    protected StudentEnrolmentTestRepository repository;

    @ParameterizedTest
    @CsvFileSource(resources = "/student/enrolment/update-without-finish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
    public void test100Positive(final int recordIndex, final String code, final String course, final String motivation,
	    final String goals) {
	// HINT: this test logs in as an employer, lists his or her jobs,
	// HINT+ selects one of them, updates it, and then checks that
	// HINT+ the update has actually been performed.

	super.signIn("student3", "student3");

	super.clickOnMenu("Student", "List my enrolments");
	super.checkListingExists();
	super.sortListing(0, "asc");

	super.checkColumnHasValue(recordIndex, 0, code);
	super.clickOnListingRecord(recordIndex);
	super.checkFormExists();
	super.fillInputBoxIn("code", code);
	super.fillInputBoxIn("course", course);
	super.fillInputBoxIn("motivation", motivation);
	super.fillInputBoxIn("goals", goals);
	super.clickOnSubmit("Update");

	super.clickOnMenu("Student", "List my enrolments");
	super.checkListingExists();
	super.sortListing(0, "asc");
	super.checkColumnHasValue(recordIndex, 0, code);
	super.checkColumnHasValue(recordIndex, 1, motivation);
	super.checkColumnHasValue(recordIndex, 2, goals);

	super.clickOnListingRecord(recordIndex);
	super.checkFormExists();
	super.checkInputBoxHasValue("code", code);
	super.checkInputBoxHasValue("course", course);
	super.checkInputBoxHasValue("motivation", motivation);
	super.checkInputBoxHasValue("goals", goals);

	super.signOut();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/student/enrolment/update-and-finish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
    public void test101Positive(final int recordIndex, final String code, final String course, final String motivation,
	    final String goals, final String creditCardNumber, final String expiryDate, final String cvc,
	    final String holderName) {
	// HINT: this test logs in as an employer, lists his or her jobs,
	// HINT+ selects one of them, updates it, and then checks that
	// HINT+ the update has actually been performed.

	super.signIn("student3", "student3");

	super.clickOnMenu("Student", "List my enrolments");
	super.checkListingExists();
	super.sortListing(0, "asc");

	super.checkColumnHasValue(recordIndex, 0, code);
	super.clickOnListingRecord(recordIndex);
	super.checkFormExists();
	super.fillInputBoxIn("code", code);
	super.fillInputBoxIn("course", course);
	super.fillInputBoxIn("motivation", motivation);
	super.fillInputBoxIn("goals", goals);
	super.fillInputBoxIn("creditCardNumber", creditCardNumber);
	super.fillInputBoxIn("expiryDate", expiryDate);
	super.fillInputBoxIn("holderName", holderName);
	super.fillInputBoxIn("cvc", cvc);
	super.fillInputBoxIn("confirmed", "true");
	super.clickOnSubmit("Update");

	super.clickOnMenu("Student", "List my enrolments");
	super.checkListingExists();
	super.sortListing(0, "asc");
	super.checkColumnHasValue(recordIndex, 0, code);
	super.checkColumnHasValue(recordIndex, 1, motivation);
	super.checkColumnHasValue(recordIndex, 2, goals);

	super.clickOnListingRecord(recordIndex);
	super.checkNotButtonExists("Update");
	super.checkInputBoxHasValue("code", code);
	super.checkInputBoxHasValue("course", course);
	super.checkInputBoxHasValue("motivation", motivation);
	super.checkInputBoxHasValue("goals", goals);
	super.checkInputBoxHasValue("holderName", holderName);
	super.checkInputBoxHasValue("lowerNibble", creditCardNumber.substring(creditCardNumber.length() - 4));
	super.checkInputBoxHasValue("workTime", "0.00");

	super.signOut();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/student/enrolment/update-without-finish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
    public void test200Negative(final int recordIndex, final String code, final String course, final String motivation,
	    final String goals) {
	// HINT: this test attempts to update a job with wrong data.

	super.signIn("student3", "student3");

	super.clickOnMenu("Student", "List my enrolments");
	super.checkListingExists();
	super.sortListing(0, "asc");

	super.clickOnListingRecord(recordIndex);
	super.checkFormExists();
	super.fillInputBoxIn("code", code);
	super.fillInputBoxIn("course", course);
	super.fillInputBoxIn("motivation", motivation);
	super.fillInputBoxIn("goals", goals);
	super.clickOnSubmit("Update");

	super.checkErrorsExist();

	super.signOut();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/student/enrolment/update-and-finish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
    public void test201Negative(final int recordIndex, final String code, final String course, final String motivation,
	    final String goals, final String creditCardNumber, final String expiryDate, final String cvc,
	    final String holderName) {
	// HINT: this test attempts to update a job with wrong data.

	super.signIn("student3", "student3");

	super.clickOnMenu("Student", "List my enrolments");
	super.checkListingExists();
	super.sortListing(0, "asc");

	super.clickOnListingRecord(recordIndex);
	super.checkFormExists();
	super.fillInputBoxIn("code", code);
	super.fillInputBoxIn("course", course);
	super.fillInputBoxIn("motivation", motivation);
	super.fillInputBoxIn("goals", goals);
	super.fillInputBoxIn("creditCardNumber", creditCardNumber);
	super.fillInputBoxIn("expiryDate", expiryDate);
	super.fillInputBoxIn("holderName", holderName);
	super.fillInputBoxIn("cvc", cvc);
	super.fillInputBoxIn("confirmed", "true");
	super.clickOnSubmit("Update");

	super.checkErrorsExist();

	super.signOut();
    }

    @Test
    public void test300Hacking() {

	Collection<Enrolment> allEnrolments;
	Collection<Enrolment> finishedEnrolments;
	String param;
	final int recordId = 1;

	allEnrolments = this.repository.findManyEnrolmentsByStudentUsername("student3");
	for (final Enrolment enrolment : allEnrolments) {
	    param = String.format("id=%d", enrolment.getId());

	    super.checkLinkExists("Sign in");
	    super.request("/student/enrolment/update", param);
	    super.checkPanicExists();

	    super.signIn("administrator", "administrator");
	    super.request("/student/enrolment/update", param);
	    super.checkPanicExists();
	    super.signOut();

	    super.signIn("student2", "student2");
	    super.request("/student/enrolment/update", param);
	    super.checkPanicExists();
	    super.signOut();
	}

	super.signIn("student3", "student3");
	super.clickOnMenu("Student", "List my enrolments");
	super.checkListingExists();
	super.sortListing(0, "asc");
	super.clickOnListingRecord(recordId);
	super.checkFormExists();
	super.fillInputBoxIn("creditCardNumber", "26000000000000");
	super.fillInputBoxIn("expiryDate", "12/24");
	super.fillInputBoxIn("holderName", "Lorem ipsum");
	super.fillInputBoxIn("cvc", "123");
	super.fillInputBoxIn("confirmed", "true");
	super.clickOnSubmit("Update");

	// WITH THIS I M TRYING TO HACK A ALREADY FINISHED ENROLMENT WITH THE CORRECT
	// USER
	finishedEnrolments = this.repository.findManyEnrolmentsByStudentUsernameAndDraftModeIsFalse("student3");
	for (final Enrolment finishedEnrolment : finishedEnrolments) {
	    param = String.format("id=%d", finishedEnrolment.getId());
	    super.request("/student/enrolment/update", param);
	    super.checkPanicExists();
	}
    }

}
