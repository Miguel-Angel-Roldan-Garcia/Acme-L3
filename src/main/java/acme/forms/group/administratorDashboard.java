package acme.forms.group;

import java.util.Map;

public class administratorDashboard {

	// Serialisation identifier -----------------------------------------------

	private static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

	protected Map<String, Integer> totalNumberOfPrincipalsPerRole;

	protected Double averagePeepsWithEmailAndLink;

	protected Double averageCriticalBulletins;

	protected Double averageNonCriticalBulletins;

	// Budget in the offers grouped by currency

	protected Double averageBudgetOffersGroupedByCurrency;

	protected Double minBudgetOfferGroupedByCurrency;

	protected Double maxBudgetOfferGroupedByCurrency;

	protected Double deviationBudgetOffersGroupedByCurrency;

	// Number of notes posted over the last 10 weeks

	protected Double averageNotesOverLast10Weeks;

	protected Double minNotesOverLast10Weeks;

	protected Double maxNotesOverLast10Weeks;

	protected Double deviationNotesOverLast10Weeks;

}
