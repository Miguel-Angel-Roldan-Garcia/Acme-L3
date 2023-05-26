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

import acme.entities.individual.assistants.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorialRecordIndex, final String code, final String tutorialTitle, final int tutorialSessionRecordIndex, final String tutorialSessionTitle, final String nature, final String startDate) {
		// HINT: this test authenticates as an assistant, list his or her tutorials, navigates
		// HINT+ to a tutorial and lists its sessions. Then deletes one, and checks that it's 
		// HINT+ been deleted properly.

		String currentQuery;

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(tutorialRecordIndex, 0, code);
		super.checkColumnHasValue(tutorialRecordIndex, 1, tutorialTitle);
		super.clickOnListingRecord(tutorialRecordIndex);
		super.clickOnButton("Sessions");
		super.checkListingExists();
		super.checkColumnHasValue(tutorialSessionRecordIndex, 0, tutorialSessionTitle);
		super.checkColumnHasValue(tutorialSessionRecordIndex, 1, nature);
		super.checkColumnHasValue(tutorialSessionRecordIndex, 2, startDate);
		super.clickOnListingRecord(tutorialSessionRecordIndex);
		super.checkFormExists();

		currentQuery = super.getCurrentQuery();
		if (currentQuery.charAt(0) == '?')
			currentQuery = currentQuery.substring(1);

		super.clickOnSubmit("Delete");

		super.request("/assistant/tutorial-session/show", currentQuery);
		super.checkPanicExists();

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a delete
		// HINT+ that doesn't involve entering any data in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to delete a tutorialSession of a tutorial as a principal without
		// HINT: the "Assistant" role.

		Collection<TutorialSession> tutorialSessions;
		String param;

		tutorialSessions = this.repository.findManyTutorialSessionsByAssistantUsername("assistant2");
		for (final TutorialSession tutorialSession : tutorialSessions) {
			param = String.format("masterId=%d", tutorialSession.getId());

			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to delete a tutorialSession of a published tutorial created by
		// HINT+ the principal.

		Collection<TutorialSession> tutorialSessions;
		String param;

		super.signIn("assistant2", "assistant2");
		tutorialSessions = this.repository.findManyTutorialSessionsByAssistantUsername("assistant2");
		for (final TutorialSession tutorialSession : tutorialSessions)
			if (!tutorialSession.getTutorial().isDraftMode()) {
				param = String.format("masterId=%d", tutorialSession.getId());
				super.request("/assistant/tutorial-session/delete", param);
				super.checkPanicExists();
			}

		super.signOut();
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to delete tutorial sessions of tutorials that weren't created
		// HINT+ by the principal.

		Collection<TutorialSession> tutorialSessions;
		String param;

		super.signIn("assistant1", "assistant1");
		tutorialSessions = this.repository.findManyTutorialSessionsByAssistantUsername("assistant2");
		for (final TutorialSession tutorialSession : tutorialSessions) {
			param = String.format("masterId=%d", tutorialSession.getId());
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();
		}

		super.signOut();
	}

}
