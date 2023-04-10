package acme.forms.group;

import java.util.Map;

import acme.datatypes.Statistic;

public class AdministratorDashboard {

    // Serialisation identifier -----------------------------------------------

    private static final long serialVersionUID = 1L;

    // Attributes -------------------------------------------------------------

    protected Map<String, Integer> totalNumberOfPrincipalsPerRole;

    // Only average

    protected double peepsWithEmailAndLink;

    // Critical and not critical bulletins
    // Boolean = true it means critical

    protected Map<Boolean, Integer> bulletins;

    // Budget in the offers grouped by currency

    protected Map<String, Statistic> budgetOffers;

    // Number of notes posted over the last 10 weeks

    protected Statistic notesOverLast10Weeks;

}
