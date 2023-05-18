//package acme.testing.student.enrolment;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//
//import acme.testing.TestHarness;
//
//public class StudentEnrolmentListMineTest extends TestHarness {
//
//    @ParameterizedTest
//    @CsvFileSource(resources = "/student/enrolment/list-mine-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
//    public void test100Positive(final int recordIndex, final String code, final String motivation, final String goal) {
//	// EN MI CASO, TENDRÁ QUE SER: CODE, MOTIVATION, GOAL
//
//	super.signIn("student1", "student1");
//
//	super.clickOnMenu("Student", "List my enrolments");
//	super.checkListingExists();
//	super.sortListing(0, "asc");
//
//	super.checkColumnHasValue(recordIndex, 0, code);
//	super.checkColumnHasValue(recordIndex, 1, motivation);
//	super.checkColumnHasValue(recordIndex, 2, goal);
//
//	super.signOut();
//    }
//
//    @Test
//    public void test200Negative() {
//	// HINT: there aren't any negative tests for this feature since it's a listing
//	// that
//	// HINT+ doesn't involve entering any data into any forms.
//    }
//
//    @Test
//    public void test300Hacking() {
//	super.checkLinkExists("Sign in");
//	super.request("/student/enrolment/list-mine");
//	super.checkPanicExists();
//
//	super.signIn("administrator", "administrator");
//	super.request("/student/enrolment/list-mine");
//	super.checkPanicExists();
//	super.signOut();
//
//	super.signIn("lecturer1", "lecturer1");
//	super.request("/student/enrolment/list-mine");
//	super.checkPanicExists();
//	super.signOut();
//
//	super.signIn("company1", "company1");
//	super.request("/student/enrolment/list-mine");
//	super.checkPanicExists();
//	super.signOut();
//
//	super.signIn("assistant1", "assistant1");
//	super.request("/student/enrolment/list-mine");
//	super.checkPanicExists();
//	super.signOut();
//
//	super.signIn("auditor1", "auditor1");
//	super.request("/student/enrolment/list-mine");
//	super.checkPanicExists();
//	super.signOut();
//    }
//
//}
