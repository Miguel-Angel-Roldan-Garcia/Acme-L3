/*
 * AssistantTutorialListMineTest.java
 *
 * Copyright (C) 2022-2023 Miguel Ángel Roldán.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.any.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyShowTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String instantiationMoment, final String title, final String nick, final String message, final String email, final String link) {
		// HINT: this test lists all peeps, clicks on one of them 
		// HINT+ and then checks that the listing has the expected data.

		super.clickOnMenu("Any", "List published peeps");
		super.checkListingExists();
		super.sortListing(2, "asc");

		super.checkColumnHasValue(recordIndex, 0, instantiationMoment);
		super.checkColumnHasValue(recordIndex, 1, nick);
		super.checkColumnHasValue(recordIndex, 2, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("instantiationMoment", instantiationMoment);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("nick", nick);
		super.checkInputBoxHasValue("message", message);
		super.checkInputBoxHasValue("email", email);
		super.checkInputBoxHasValue("link", link);
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature since it's a listing that
		// HINT+ doesn't involve entering any data into any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: there aren't any hacking tests for this feature since it's one that
		// HINT+ everyone can access, that is, any principals.
	}

}
