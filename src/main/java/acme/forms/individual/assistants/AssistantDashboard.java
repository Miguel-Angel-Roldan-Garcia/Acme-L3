
package acme.forms.individual.assistants;

import java.util.Map;

import acme.framework.data.AbstractForm;

public class AssistantDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Possible keys: Theory, HandsOn
	protected Map<String, Integer>	totalNumberOfCourses;

	// Session related attributes ---------------------------------------------

	// Possible keys: average, deviation, maximum, minimum
	protected Map<String, Integer>	timeOfSessions;

	// Tutorial related attributes --------------------------------------------

	// Possible keys: average, deviation, maximum, minimum
	protected Map<String, Integer>	timeOfTutorials;

}
