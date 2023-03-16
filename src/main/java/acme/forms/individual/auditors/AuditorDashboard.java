
package acme.forms.individual.auditors;

import acme.framework.data.AbstractForm;

public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	protected Integer			totalAudits;

	protected Double			averageAuditingRecordPerAudit;

	protected Double			deviationAuditingRecordPerAudit;

	protected Double			minimumAuditingRecordPerAudit;

	protected Double			maximumAuditingRecordPerAudit;

	protected Double			averageAuditPeriod;

	protected Double			deviationAuditPeriod;

	protected Double			minimumAuditPeriod;

	protected Double			maximumAuditPeriod;

}
