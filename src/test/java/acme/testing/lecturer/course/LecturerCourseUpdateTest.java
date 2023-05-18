
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.individual.lectures.Course;
import acme.testing.TestHarness;

public class LecturerCourseUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abstract$, final String link, final String retailPrice) {
		// HINT: this test logs in as an lecturer, lists his or her courses, 
		// HINT+ selects one of them, updates it, and then checks that 
		// HINT+ the update has actually been performed.
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "List my courses");
		super.checkListingExists();
		super.sortListing(0, "desc");
		

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.clickOnSubmit("Update Course");

		super.checkListingExists();
		super.sortListing(0, "desc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, retailPrice);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("retailPrice", retailPrice);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String abstract$, final String link, final String retailPrice) {
		// HINT: this test attempts to update a course with wrong data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "List my courses");
		super.checkListingExists();
		super.sortListing(0, "desc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.clickOnSubmit("Update Course");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a course with a role other than "Employer",
		// HINT+ or using an lecturer who is not the owner.

		Collection<Course>	courses;
		String			param;
		int i = 1;
		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course course : courses) {
			if(i>=2) break;
			i++;
			param = String.format("id=%d", course.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
