
package acme.forms.individual.auditors;

import java.util.Map;

import acme.entities.individual.auditors.AuditingRecord;
import acme.framework.data.AbstractForm;

public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------
	private static final long				serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	protected Map<String, Integer>			totalAudits;

	// Auditing records in their audits

	protected Map<AuditingRecord, Double>	averageAuditingRecordPerAudit;

	protected Map<AuditingRecord, Double>	deviationAuditingRecordPerAudit;

	protected Map<AuditingRecord, Double>	minimumAuditingRecordPerAudit;

	protected Map<AuditingRecord, Double>	maximumAuditingRecordPerAudit;

	// Audit period length

	protected double						averageAuditPeriod;

	protected double						deviationAuditPeriod;

	protected double						minimumAuditPeriod;

	protected double						maximumAuditPeriod;

}
