
package acme.forms.individual.assistants;

import java.util.Map;

import acme.datatypes.Nature;
import acme.datatypes.Statistic;
import acme.framework.data.AbstractForm;

public class AssistantDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Possible keys: Theory, HandsOn
	protected Map<Nature, Integer>	courseCount;

	// Session related attributes ---------------------------------------------

	// Possible keys: average, deviation, maximum, minimum
	protected Statistic				timeInSession;

	// Tutorial related attributes --------------------------------------------

	// Possible keys: average, deviation, maximum, minimum
	protected Statistic				timeInTutorial;

}
