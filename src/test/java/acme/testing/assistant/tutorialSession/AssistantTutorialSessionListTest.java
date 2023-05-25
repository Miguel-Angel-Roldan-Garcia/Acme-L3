/*
 * AssistantTutorialSessionListTest.java
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

public class AssistantTutorialSessionListTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorialRecordIndex, final String code, final String tutorialTitle, final int tutorialSessionRecordIndex, final String tutorialSessionTitle, final String nature, final String startDate) {
		// HINT: this test authenticates as an assistant, then lists his or her tutorials,
		// HINT+ selects one of them, and check that it has the expected duties.

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

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this listing, since it doesn't
		// HINT+ involve filling in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to list the duties of a tutorial that is unpublished
		// HINT+ using a principal that didn't create it.

		Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");
		for (final Tutorial tutorial : tutorials)
			if (tutorial.isDraftMode()) {
				param = String.format("masterId=%d", tutorial.getId());

				super.checkLinkExists("Sign in");
				super.request("/assistant/tutorial-session/list", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/assistant/tutorial-session/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant2", "assistant2");
				super.request("/assistant/tutorial-session/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/assistant/tutorial-session/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/assistant/tutorial-session/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/assistant/tutorial-session/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/assistant/tutorial-session/list", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
