
package acme.forms.individual.assistants;

import java.util.Map;

import acme.datatypes.Nature;
import acme.datatypes.Statistic;
import acme.framework.data.AbstractForm;

public class AssistantDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	protected Map<Nature, Integer>	tutorialCount;

	// Session related attributes ---------------------------------------------

	protected Statistic				SessionTimeStatistics;

	// Tutorial related attributes --------------------------------------------

	protected Statistic				tutorialTimeStatistics;

}
