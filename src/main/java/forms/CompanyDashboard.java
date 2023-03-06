package forms;

import java.util.Map;

import acme.entities.Practicum;
import acme.framework.data.AbstractForm;

public class CompanyDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------

	protected Map<String, Integer> totalPracticumByMonthInLastYear;

	// Session duration per practicum

	protected Map<Practicum, Double> averageSessionsDurationPerPracticum;

	protected Map<Practicum, Double> deviationSessionsDurationPerPracticum;

	protected Map<Practicum, Double> minimumSessionDurationPerPracticum;

	protected Map<Practicum, Double> maximumSessionDurationPerPracticum;

	// Practicum duration

	protected double averagePracticumDuration;

	protected double deviationPracticumDuration;

	protected double minimumPracticumDuration;

	protected double maximumPracticumDuration;

}
