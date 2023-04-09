
package acme.forms.individual.companies;

import acme.datatypes.Statistic;
import acme.framework.data.AbstractForm;

public class CompanyDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------

	protected int[] practicumCountByMonth;

	// Session and practicum duration

	protected Statistic practicumPeriodLenght;

	protected Statistic practicumSessionsPeriodLenght;

}
