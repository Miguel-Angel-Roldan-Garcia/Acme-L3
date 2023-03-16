package acme.forms.individual.companies;

import java.util.Map;

import acme.framework.data.AbstractForm;

public class CompanyDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------

	protected Map<Integer, Integer> totalPracticumByMonthInLastYear;

	// Session duration per practicum

	protected double averageSessionsPracticumDuration;

	protected double deviationSessionsPracticumDuration;

	protected double minimumSessionPracticumDurationPerPracticum;

	protected double maximumSessionPracticumDuration;

	// Practicum duration

	protected double averagePracticumDuration;

	protected double deviationPracticumDuration;

	protected double minimumPracticumDuration;

	protected double maximumPracticumDuration;

}
