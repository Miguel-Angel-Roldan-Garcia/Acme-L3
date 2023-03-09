
package forms;

import acme.framework.data.AbstractForm;

public class AssistantDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	protected Integer			totalNumberOfTheoryCourses;

	protected Integer			totalNumberOfHandsOnCourses;

	// Session related attributes ---------------------------------------------

	protected Double			averageTimeOfSessions;

	protected Double			deviationTimeOfSessions;

	protected Double			minimumTimeOfSessions;

	protected Double			maximumTimeOfSessions;

	// Tutorial related attributes --------------------------------------------

	protected Double			averageTimeOfTutorials;

	protected Double			deviationTimeOfTutorials;

	protected Double			minimumTimeOfTutorials;

	protected Double			maximumTimeOfTutorials;
}
