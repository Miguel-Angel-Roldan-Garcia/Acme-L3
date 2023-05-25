/*
 * AssistantTutorialSessionCreateTest.java
 *
 * Copyright (C) 2012-2023 Miguel Ángel Roldán.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.individual.assistants.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialSessionCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorialRecordIndex, final String code, final String tutorialTitle, final int tutorialSessionRecordIndex, final String tutorialSessionTitle, final String abstract$, final String nature, final String startDate,
		final String endDate, final String link) {
		// HINT: this test authenticates as an assistant, list his or her tutorials, navigates
		// HINT+ to a tutorial and lists its sessions. Then creates a new one, and check that it's 
		// HINT+ been created properly.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(tutorialRecordIndex, 0, code);
		super.checkColumnHasValue(tutorialRecordIndex, 1, tutorialTitle);
		super.clickOnListingRecord(tutorialRecordIndex);
		super.clickOnButton("Sessions");

		super.clickOnButton("Create");
		super.fillInputBoxIn("title", tutorialSessionTitle);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("nature", nature);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(tutorialSessionRecordIndex, 0, tutorialSessionTitle);
		super.checkColumnHasValue(tutorialSessionRecordIndex, 1, nature);
		super.checkColumnHasValue(tutorialSessionRecordIndex, 2, startDate);

		super.clickOnListingRecord(tutorialSessionRecordIndex);
		super.checkInputBoxHasValue("title", tutorialSessionTitle);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("nature", nature);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int tutorialRecordIndex, final String code, final String tutorialTitle, final int tutorialSessionRecordIndex, final String tutorialSessionTitle, final String abstract$, final String nature, final String startDate,
		final String endDate, final String link) {
		// HINT: this test attempts to create tutorial sessions using wrong data.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(tutorialRecordIndex, 0, code);
		super.checkColumnHasValue(tutorialRecordIndex, 1, tutorialTitle);
		super.clickOnListingRecord(tutorialRecordIndex);
		super.clickOnButton("Sessions");

		super.clickOnButton("Create");
		super.fillInputBoxIn("title", tutorialSessionTitle);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("nature", nature);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a tutorialSession for a tutorial as a principal without
		// HINT: the "Assistant" role.

		Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("masterId=%d", tutorial.getId());

			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial-session/create", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/assistant/tutorial-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/tutorial-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/assistant/tutorial-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/assistant/tutorial-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/assistant/tutorial-session/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to create a tutorialSession for a published tutorial created by
		// HINT+ the principal.

		Collection<Tutorial> tutorials;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");
		for (final Tutorial tutorial : tutorials)
			if (!tutorial.isDraftMode()) {
				param = String.format("masterId=%d", tutorial.getId());
				super.request("/assistant/tutorial-session/create", param);
				super.checkPanicExists();
			}
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to create tutorial sessions for tutorials that weren't created
		// HINT+ by the principal.

		Collection<Tutorial> tutorials;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant2");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("masterId=%d", tutorial.getId());
			super.request("/assistant/tutorial-session/create", param);
			super.checkPanicExists();
		}
	}

}
