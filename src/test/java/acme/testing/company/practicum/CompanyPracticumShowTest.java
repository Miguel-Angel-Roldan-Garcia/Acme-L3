/*
 * EmployerJobShowTest.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.company.practicum;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;

import acme.testing.TestHarness;

public class CompanyPracticumShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumRepository repository;

	// Test data --------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/employer/job/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abstract$, final String goals, final String estimatedTotalTime, final String courseCode, final String moreInfo, final String description) {
		// HINT: this test signs in as an employer, lists all of the jobs, click on  
		// HINT+ one of them, and checks that the form has the expected data.

		super.signIn("company1", "company1");

		super.clickOnMenu("Student", "List my enrolments");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract", abstract$);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("estimatedTotalTime", estimatedTotalTime);
		super.checkInputBoxHasValue("courseCode", courseCode);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a listing
		// HINT+ that doesn't involve entering any data in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show an unpublished job by someone who is not the principal.

		Collection<Job> jobs;
		String param;

		jobs = this.repository.findManyJobsByEmployerUsername("employer1");
		for (final Job job : jobs)
			if (job.isDraftMode()) {
				param = String.format("id=%d", job.getId());

				super.checkLinkExists("Sign in");
				super.request("/employer/job/show", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/employer/job/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("employer2", "employer2");
				super.request("/employer/job/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("worker1", "worker1");
				super.request("/employer/job/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
