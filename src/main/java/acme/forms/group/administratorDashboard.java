package acme.forms.group;

import java.util.Map;

import acme.datatypes.Statistic;

public class administratorDashboard {

	// Serialisation identifier -----------------------------------------------

	private static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

	protected Map<String, Integer> totalNumberOfPrincipalsPerRole;

	// Only average

	protected Statistic peepsWithEmailAndLink;

	protected Statistic criticalBulletins;

	protected Statistic nonCriticalBulletins;

	// Budget in the offers grouped by currency

	protected Statistic budgetOffersGroupedByCurrency;

	// Number of notes posted over the last 10 weeks

	protected Statistic notesOverLast10Weeks;

}
