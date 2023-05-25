/*
 * CompanyPracticumSessionUpdateTest.java
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

import acme.entities.individual.assistants.Tutorial;
import acme.entities.individual.companies.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecordIndex, final String code, final String practicumTitle, final int practicumSessionRecordIndex, final String practicumSessionTitle, final String abstract$, final String startDate, final String endDate,
		final String link) {
		// HINT: this test authenticates as a company, list his or her practica, navigates
		// HINT+ to a practicum and lists its sessions. Then updates one, and checks that the 
		// HINT+ update has actually been performed.

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
		super.clickOnListingRecord(practicumSessionRecordIndex);
		
		super.checkFormExists();
		super.fillInputBoxIn("title", practicumSessionTitle);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(practicumSessionRecordIndex, 0, practicumSessionTitle);
		super.checkColumnHasValue(practicumSessionRecordIndex, 1, startDate);
		super.checkColumnHasValue(practicumSessionRecordIndex, 2, endDate);

		super.clickOnListingRecord(practicumSessionRecordIndex);
		super.checkInputBoxHasValue("title", practicumSessionTitle);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int practicumRecordIndex, final String code, final String practicumTitle, final int practicumSessionRecordIndex, final String practicumSessionTitle, final String abstract$, final String startDate, final String endDate,
		final String link) {
		// HINT: this test attempts to update practicum sessions using wrong data.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "List my practica");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(practicumRecordIndex, 0, code);
		super.checkColumnHasValue(practicumRecordIndex, 1, practicumTitle);
		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("Practicum sessions");
		
		super.checkListingExists();
		super.clickOnListingRecord(practicumSessionRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("title", practicumSessionTitle);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a practicumSession of a practicum as a principal without
		// HINT: the "Company" role.
		// HINT+ or using a company who is not the owner.


		Collection<PracticumSession> practicumSessions;
		String param;

		practicumSessions = this.repository.findManyPracticumSessionsByCompanyUsername("company1");
		for (final PracticumSession practicumSession : practicumSessions) {
			param = String.format("id=%d", practicumSession.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();
			
			super.signIn("assistant1", "assistant1");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to update a practicumSession of a published practicum created by
		// HINT+ the principal.

		Collection<PracticumSession> practicumSessions;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practicumSessions = this.repository.findManyPracticumSessionsByCompanyUsername("company1");
		for (final PracticumSession practicumSession : practicumSessions)
			if (!practicumSession.isDraftMode()) {
				param = String.format("id=%d", practicumSession.getId());
				super.request("/company/practicum-session/update", param);
				super.checkPanicExists();
			}
	}

}
