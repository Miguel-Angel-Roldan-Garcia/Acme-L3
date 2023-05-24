/*
 * CompanyPracticumSessionCreateTest.java
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

import acme.entities.individual.companies.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumSessionCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecordIndex, final String code, final String practicumTitle, final int practicumSessionRecordIndex, final String practicumSessionTitle, final String abstract$, final String startDate, final String endDate,
		final String link) {
		// HINT: this test authenticates as a company, list his or her practica, navigates
		// HINT+ to a practicum and lists its sessions. Then creates a new one, and check that it's 
		// HINT+ been created properly.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "List my practica");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(practicumRecordIndex, 0, code);
		super.checkColumnHasValue(practicumRecordIndex, 1, practicumTitle);
		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("Practicum sessions");

		super.clickOnButton("Create");
		super.fillInputBoxIn("title", practicumSessionTitle);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

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
	@CsvFileSource(resources = "/company/practicumSession/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int practicumRecordIndex, final String code, final String practicumTitle, final int practicumSessionRecordIndex, final String practicumSessionTitle, final String abstract$, final String startDate, final String endDate,
		final String link) {
		// HINT: this test attempts to create practicum sessions using wrong data.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "List my practica");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(practicumRecordIndex, 0, code);
		super.checkColumnHasValue(practicumRecordIndex, 1, practicumTitle);
		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("Practicum sessions");

		super.clickOnButton("Create");
		super.fillInputBoxIn("title", practicumSessionTitle);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a practicumSession for a practicum as a principal without
		// HINT: the "Company" role.
		// HINT+ or using a company who is not the owner.


		Collection<Practicum> practica;
		String param;

		practica = this.repository.findManyPracticaByCompanyUsername("company1");
		for (final Practicum practicum : practica) {
			param = String.format("masterId=%d", practicum.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();
			
			super.signIn("assistant1", "assistant1");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to create a practicumSession for a published practicum created by
		// HINT+ the principal.

		Collection<Practicum> practica;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practica = this.repository.findManyPracticaByCompanyUsername("company1");
		for (final Practicum practicum : practica)
			if (!practicum.isDraftMode()) {
				param = String.format("masterId=%d", practicum.getId());
				super.request("/company/practicum-session/create", param);
				super.checkPanicExists();
			}
	}

}
