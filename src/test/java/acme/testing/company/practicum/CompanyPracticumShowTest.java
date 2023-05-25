/*
 * CompanyPracticumShowTest.java
 *
 * Copyright (C) 2022-2023 Javier Fernández Castillo.
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

import acme.entities.individual.companies.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumTestRepository repository;

	// Test data --------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abstract$, final String goals, final String course) {
		// HINT: this test tries to show all practica created by the principal.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "List my practica");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("course", course);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this show

	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show an unpublished practicum by someone who is not the principal.

		Collection<Practicum> practica;
		String param;

		practica = this.repository.findManyPracticaByCompanyUsername("company1");
		for (final Practicum practicum : practica)
			if (practicum.isDraftMode()) {
				param = String.format("id=%d", practicum.getId());

				super.checkLinkExists("Sign in");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company2", "company2");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
