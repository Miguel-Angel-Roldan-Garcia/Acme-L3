/*
 * CompanyPracticumSessionShowTest.java
 *
 * Copyright (C) 2022-2023 Javier Fernández Castillo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.individual.companies.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest

	@CsvFileSource(resources = "/company/practicumSession/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecordIndex, final String code, final String practicumTitle, final int practicumSessionRecordIndex, final String practicumSessionTitle, final String abstract$, final String startDate, final String endDate,
		final String link) {

		// HINT: this test signs in as a company, lists his or her practica, selects
		// HINT+ one of them and checks that it's as expected.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "List my practica");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(practicumRecordIndex, 0, code);
		super.checkColumnHasValue(practicumRecordIndex, 1, practicumTitle);
		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("Practicum sessions");
		
		super.checkListingExists();
		super.checkColumnHasValue(practicumSessionRecordIndex, 0, practicumSessionTitle);
		super.checkColumnHasValue(practicumSessionRecordIndex, 1, startDate);
		super.checkColumnHasValue(practicumSessionRecordIndex, 2, endDate);
		super.clickOnListingRecord(practicumSessionRecordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", practicumSessionTitle);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this show
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show a practicumSession of a practicum that is in draft mode or
		// HINT+ not available, but wasn't published by the principal;

		Collection<PracticumSession> practicumSessions;
		String param;

		practicumSessions = this.repository.findManyPracticumSessionsByCompanyUsername("company1");
		for (final PracticumSession practicumSession : practicumSessions)
			if (practicumSession.getPracticum().isDraftMode()) {
				param = String.format("id=%d", practicumSession.getPracticum().getId());

				super.checkLinkExists("Sign in");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company2", "company2");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

			}
	}

}
