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

import acme.testing.TestHarness;

public class AssistantTutorialSessionCreateTest extends TestHarness {
	/*
	 * // Internal state ---------------------------------------------------------
	 * 
	 * @Autowired
	 * protected AssistantTutorialSessionTestRepository repository;
	 * 
	 * // Test methods -----------------------------------------------------------
	 * 
	 * 
	 * @ParameterizedTest
	 * 
	 * @CsvFileSource(resources = "/employer/duty/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	 * public void test100Positive(final int jobRecordIndex, final int dutyRecordIndex, final String title, final String description, final String workLoad, final String moreInfo) {
	 * // HINT: this test authenticates as an employer, list his or her jobs, navigates
	 * // HINT+ to their duties, and checks that they have the expected data.
	 * 
	 * super.signIn("employer1", "employer1");
	 * 
	 * super.clickOnMenu("Employer", "List my jobs");
	 * super.checkListingExists();
	 * super.sortListing(0, "asc");
	 * 
	 * super.clickOnListingRecord(jobRecordIndex);
	 * super.clickOnButton("Duties");
	 * 
	 * super.clickOnButton("Create");
	 * super.fillInputBoxIn("title", title);
	 * super.fillInputBoxIn("description", description);
	 * super.fillInputBoxIn("workLoad", workLoad);
	 * super.fillInputBoxIn("moreInfo", moreInfo);
	 * super.clickOnSubmit("Create");
	 * 
	 * super.checkListingExists();
	 * super.sortListing(0, "asc");
	 * super.checkColumnHasValue(dutyRecordIndex, 0, title);
	 * super.checkColumnHasValue(dutyRecordIndex, 1, workLoad);
	 * 
	 * super.clickOnListingRecord(dutyRecordIndex);
	 * super.checkInputBoxHasValue("title", title);
	 * super.checkInputBoxHasValue("description", description);
	 * super.checkInputBoxHasValue("workLoad", workLoad);
	 * super.checkInputBoxHasValue("moreInfo", moreInfo);
	 * 
	 * super.signOut();
	 * }
	 * 
	 * @ParameterizedTest
	 * 
	 * @CsvFileSource(resources = "/employer/duty/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	 * public void test200Negative(final int jobRecordIndex, final int dutyRecordIndex, final String title, final String description, final String workLoad, final String moreInfo) {
	 * // HINT: this test attempts to create duties using wrong data.
	 * 
	 * super.signIn("employer1", "employer1");
	 * 
	 * super.clickOnMenu("Employer", "List my jobs");
	 * super.checkListingExists();
	 * super.sortListing(0, "asc");
	 * 
	 * super.clickOnListingRecord(jobRecordIndex);
	 * super.clickOnButton("Duties");
	 * 
	 * super.clickOnButton("Create");
	 * super.fillInputBoxIn("title", title);
	 * super.fillInputBoxIn("description", description);
	 * super.fillInputBoxIn("workLoad", workLoad);
	 * super.fillInputBoxIn("moreInfo", moreInfo);
	 * super.clickOnSubmit("Create");
	 * super.checkErrorsExist();
	 * 
	 * super.signOut();
	 * }
	 * 
	 * @Test
	 * public void test300Hacking() {
	 * // HINT: this test tries to create a duty for a job as a principal without
	 * // HINT: the "Employer" role.
	 * 
	 * Collection<Job> jobs;
	 * String param;
	 * 
	 * jobs = this.repository.findManyJobsByEmployerUsername("employer1");
	 * for (final Job job : jobs) {
	 * param = String.format("masterId=%d", job.getId());
	 * 
	 * super.checkLinkExists("Sign in");
	 * super.request("/employer/duty/create", param);
	 * super.checkPanicExists();
	 * 
	 * super.signIn("administrator", "administrator");
	 * super.request("/employer/duty/create", param);
	 * super.checkPanicExists();
	 * super.signOut();
	 * 
	 * super.signIn("worker1", "worker1");
	 * super.request("/employer/duty/create", param);
	 * super.checkPanicExists();
	 * super.signOut();
	 * }
	 * }
	 * 
	 * @Test
	 * public void test301Hacking() {
	 * // HINT: this test tries to create a duty for a published job created by
	 * // HINT+ the principal.
	 * 
	 * Collection<Job> jobs;
	 * String param;
	 * 
	 * super.checkLinkExists("Sign in");
	 * super.signIn("employer1", "employer1");
	 * jobs = this.repository.findManyJobsByEmployerUsername("employer1");
	 * for (final Job job : jobs)
	 * if (!job.isDraftMode()) {
	 * param = String.format("masterId=%d", job.getId());
	 * super.request("/employer/duty/create", param);
	 * super.checkPanicExists();
	 * }
	 * }
	 * 
	 * @Test
	 * public void test302Hacking() {
	 * // HINT: this test tries to create duties for jobs that weren't created
	 * // HINT+ by the principal.
	 * 
	 * Collection<Job> jobs;
	 * String param;
	 * 
	 * super.checkLinkExists("Sign in");
	 * super.signIn("employer1", "employer1");
	 * jobs = this.repository.findManyJobsByEmployerUsername("employer2");
	 * for (final Job job : jobs) {
	 * param = String.format("masterId=%d", job.getId());
	 * super.request("/employer/duty/create", param);
	 * super.checkPanicExists();
	 * }
	 * }
	 */
}
